package com.flx.netty.chat.common.utils.security.cipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

import static com.flx.netty.chat.common.constants.SecurityConstant.AES;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 19:11
 * @Description:
 *
 * 对称加密方法
 *
 * 密钥管理：比较难，比较快，不适合互联网，一般用于内部系统，适合大数据量的加解密处理
 *
 * AES加密算法是密码学中的 高级加密标准，该加密算法采用对称分组密码体制，密钥长度的最少支持为 128 位、 192 位、256 位，
 * 分组长度 128 位，算法应易于各种硬件和软件实现。这种加密算法是美国联邦政府采用的 区块加密标准。
 * AES本身就是为了取代DES的，AES具有更好的安全性、效率和灵活性。
 *
 */
public class AESUtils {

    public static void main(String[] args) {

        byte[] encrypt = encrypt("Hello,World！".getBytes(), "fenglixiong");
        byte[] decrypt = decrypt(encrypt, "fenglixiong");
        System.out.println(new String(decrypt));

    }

    /**
     * 采用的AES密钥长度
     * 128 256 ...
     */
    private final static int DEFAULT_KEY_SIZE = 256;

    /**
     * 加密算法
     * 采用AES加密+Base64编码+十进制编码
     * @param source
     * @param secretKey
     * @return
     */
    public static byte[] encrypt(byte[] source,String secretKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(genKey(secretKey),AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密算法
     * 采用十进制解码+Base64解码+AES解码
     * @param source
     * @param secretKey
     * @return
     */
    public static byte[] decrypt(byte[] source,String secretKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(genKey(secretKey),AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取随机密钥
     */
    private static byte[] genKey(String secretKey) {
        try {
            KeyGenerator gen = KeyGenerator.getInstance(AES);
            gen.init(DEFAULT_KEY_SIZE, new SecureRandom(secretKey.getBytes()));
            return gen.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
