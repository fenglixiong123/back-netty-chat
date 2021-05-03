package com.flx.netty.chat.common.utils.security.cipher;

import com.flx.netty.chat.common.utils.security.codec.Base64Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import static com.flx.netty.chat.common.constants.SecurityConstant.RSA;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 20:15
 * @Description:
 *
 * 非对称加密算法
 *
 * 密钥容易管理，比较慢，适合小数据量加解密或数据签名
 *
 * RSA加密算法是目前最有影响力的公钥加密算法，并且被普遍认为是目前 最优秀的公钥方案之一。
 * RSA是第一个能同时用于加密和数字签名的算法，它能够抵抗到目前为止已知的所有密码攻击，已被 ISO 推荐为公钥数据加密标准。
 *
 */
@Slf4j
public class RSAUtils {

    public static void main(String[] args) throws Exception {
        String message = "Hello,world!";
        RsaKeyPair rsaKeyPair = genKeyPair();
        System.out.println("public = "+rsaKeyPair.getPublicKey());
        System.out.println("private = "+rsaKeyPair.getPrivateKey());
        byte[] encrypt = encrypt(message.getBytes(), rsaKeyPair.getPublicKey());
        byte[] decrypt = decrypt(encrypt, rsaKeyPair.getPrivateKey());
        System.out.println(new String(decrypt));
    }

    /**
     * 使用公钥对数据进行加密
     */
    public static byte[] encrypt(byte[] binaryData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key pubKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(binaryData);
    }

    /**
     * 使用私钥对数据进行解密
     */
    public static byte[] decrypt(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key priKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(binaryData);
    }

    /**
     * 使用私钥对数据进行加密
     */
    public static byte[] encryptByPrivate(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key priKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        return cipher.doFinal(binaryData);
    }

    /**
     * 使用公钥对数据进行解密
     */
    public static byte[] decryptByPublic(byte[] binaryData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(binaryData);
    }


    /**
     * 使用私钥对数据进行签名
     */
    public static String sign(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(binaryData);
        return Base64Utils.encodeToStr(signature.sign());
    }

    /**
     * 使用公钥对数据签名进行验证
     */
    public static boolean verify(byte[] binaryData, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Objects.requireNonNull(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(binaryData);
        return signature.verify(Base64Utils.decode(sign));
    }


    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static RsaKeyPair genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);// 基于RSA算法生成公钥和私钥对
        keyPairGen.initialize(1024,new SecureRandom());// 初始化密钥对生成器，密钥大小为96-1024位
        KeyPair keyPair = keyPairGen.generateKeyPair();// 生成一个密钥对，保存在keyPair中
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();  // 得到公钥
        String publicKeyString = Base64Utils.encodeToStr((publicKey.getEncoded()));// 得到公钥字符串
        String privateKeyString = Base64Utils.encodeToStr((privateKey.getEncoded()));// 得到私钥字符串
        return new RsaKeyPair(publicKeyString,privateKeyString);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RsaKeyPair{
        private String publicKey;
        private String privateKey;
    }

}
