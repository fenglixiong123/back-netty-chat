package com.flx.netty.chat.common.utils.security.cipher;

import com.flx.netty.chat.common.utils.security.Base64Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.flx.netty.chat.common.constants.SecurityConstant.RSA;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 20:15
 * @Description:
 *
 * RSA加密算法是目前最有影响力的公钥加密算法，并且被普遍认为是目前 最优秀的公钥方案之一。
 * RSA是第一个能同时用于加密和数字签名的算法，它能够抵抗到目前为止已知的所有密码攻击，已被 ISO 推荐为公钥数据加密标准。
 *
 */
@Slf4j
public class RSAUtils {

    @Data
    public static class RsaHelper{

        private RSAPublicKey publicKey;
        private RSAPrivateCrtKey privateKey;

        static {
            //Security.addProvider(new BouncyCastleProvider()); //使用bouncycastle作为加密算法实现
        }

        public RsaHelper(String publicKey, String privateKey) {
            this(Base64Utils.decodeByte(publicKey), Base64Utils.decodeByte(privateKey));
        }

        public RsaHelper(byte[] publicKey, byte[] privateKey) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                if (publicKey != null && publicKey.length > 0) {
                    this.publicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
                }
                if (privateKey != null && privateKey.length > 0) {
                    this.privateKey = (RSAPrivateCrtKey)keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public RsaHelper(String publicKey) {
            this(Base64Utils.decodeByte(publicKey));
        }

        public RsaHelper(byte[] publicKey) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                if (publicKey != null && publicKey.length > 0) {
                    this.publicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public byte[] encrypt(byte[] content){
            return RSAUtils.encrypt(content,publicKey);
        }

        public byte[] decrypt(byte[] secret) {
            return RSAUtils.decrypt(secret,privateKey);
        }

        public byte[] sign(byte[] content){
            return RSAUtils.sign(content,privateKey);
        }

    }


    public static byte[] encrypt(byte[] content,RSAPublicKey publicKey) {
        if (publicKey == null) {
            throw new RuntimeException("public key is null.");
        }

        if (content == null) {
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int size = publicKey.getModulus().bitLength() / 8 - 11;
            ByteArrayOutputStream baos = new ByteArrayOutputStream((content.length + size - 1) / size * (size + 11));
            int left;
            for (int i = 0; i < content.length; ) {
                left = content.length - i;
                if (left > size) {
                    cipher.update(content, i, size);
                    i += size;
                } else {
                    cipher.update(content, i, left);
                    i += left;
                }
                baos.write(cipher.doFinal());
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] secret,RSAPrivateCrtKey privateKey) {
        if (privateKey == null) {
            throw new RuntimeException("private key is null.");
        }

        if (secret == null) {
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int size = privateKey.getModulus().bitLength() / 8;
            ByteArrayOutputStream baos = new ByteArrayOutputStream((secret.length + size - 12) / (size - 11) * size);
            int left;
            for (int i = 0; i < secret.length; ) {
                left = secret.length - i;
                if (left > size) {
                    cipher.update(secret, i, size);
                    i += size;
                } else {
                    cipher.update(secret, i, left);
                    i += left;
                }
                baos.write(cipher.doFinal());
            }
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("rsa decrypt failed.", e);
        }
        return null;
    }

    public static byte[] sign(byte[] content,RSAPrivateCrtKey privateKey) {
        if (privateKey == null) {
            throw new RuntimeException("private key is null.");
        }
        if (content == null) {
            return null;
        }
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            signature.update(content);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(byte[] sign, byte[] content,RSAPublicKey publicKey) {
        if (publicKey == null) {
            throw new RuntimeException("public key is null.");
        }
        if (sign == null || content == null) {
            return false;
        }
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey);
            signature.update(content);
            return signature.verify(sign);
        } catch (Exception e) {
            log.error("rsa verify failed.", e);
        }
        return false;
    }


}
