package com.flx.netty.chat.common.utils.security.cipher;

import com.flx.netty.chat.common.utils.security.sign.Md5Utils;
import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static com.flx.netty.chat.common.constants.SecurityConstant.AES;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 19:11
 * @Description:
 *
 * 参考:
 * https://blog.csdn.net/baidu_22254181/article/details/82594072
 *
 * 对称加密方法
 *
 * AES加密算法是密码学中的 高级加密标准，该加密算法采用对称分组密码体制，密钥长度的最少支持为 128 位、 192 位、256 位，
 * 分组长度 128 位，算法应易于各种硬件和软件实现。这种加密算法是美国联邦政府采用的 区块加密标准。
 * AES本身就是为了取代DES的，AES具有更好的安全性、效率和灵活性。
 *
 */
public class AESUtils {

    @Data
    public static class AesHelper{

        private SecretKeySpec keySpec;
        private IvParameterSpec iv;

        public AesHelper(String aesKey) {
            this(aesKey.getBytes(StandardCharsets.UTF_8),null);
        }

        public AesHelper(byte[] aesKey, byte[] iv) {
            if (aesKey == null || aesKey.length < 16 || (iv != null && iv.length < 16)) {
                throw new RuntimeException("Aes is not correct !");
            }
            if (iv == null) {
                iv = Md5Utils.digest(aesKey);
            }
            keySpec = new SecretKeySpec(aesKey, AES);
            this.iv = new IvParameterSpec(iv);
        }

        public byte[] encrypt(String data) {
            return AESUtils.encrypt(data.getBytes(StandardCharsets.UTF_8),keySpec,iv);
        }

        public byte[] decrypt(String secret) {
            return AESUtils.decrypt(secret.getBytes(StandardCharsets.UTF_8),keySpec,iv);
        }

        public byte[] encrypt(byte[] data) {
            return AESUtils.encrypt(data,keySpec,iv);
        }

        public byte[] decrypt(byte[] secret) {
            return AESUtils.decrypt(secret,keySpec,iv);
        }

    }

    public AesHelper buildAesHelper(String aesKey){
        return new AesHelper(aesKey);
    }

    public static byte[] encrypt(byte[] data,SecretKeySpec keySpec,IvParameterSpec iv) {
        byte[] result = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static byte[] decrypt(byte[] secret,SecretKeySpec keySpec,IvParameterSpec iv) {
        byte[] result = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            result = cipher.doFinal(secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static byte[] randomKey(int size) {
        byte[] result = null;
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(size, new SecureRandom());
            result = gen.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
