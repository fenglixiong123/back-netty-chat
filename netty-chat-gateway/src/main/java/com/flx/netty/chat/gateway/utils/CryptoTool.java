package com.flx.netty.chat.gateway.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;


/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 12:44
 * @Description:
 */
@Slf4j
public class CryptoTool {

    public final static String AES = "AES";
    public final static String MD5 = "MD5";

    private CryptoTool() {
    }

    private static class CryptoToolHolder {
        private static final CryptoTool INSTANCE = new CryptoTool();
    }

    public static CryptoTool getCrypto() {
        return CryptoToolHolder.INSTANCE;
    }

    public static String encode64(String src) {
        return Base64.getEncoder().encodeToString(src.getBytes(StandardCharsets.UTF_8));
    }


    public static String encode64(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public static String decode64(String src) {
        byte[] asBytes = Base64.getDecoder().decode(src);
        return new String(asBytes, StandardCharsets.UTF_8);
    }

    public static String decode64(String src,String charset){
        byte[] asBytes = Base64.getMimeDecoder().decode(src);
        try {
            return new String(asBytes,Objects.requireNonNull(charset));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String encryptAES2Str(String content, String secret) {
        return byte2hex(encryptAES(content, secret));
    }

    public static String decryptAES2Str(String encryptContent, String secret) {
        return byte2Str(decryptAES(hex2Byte(encryptContent), secret));
    }

    public static byte[] encryptAES(String content, String password) {
        return initAES(content.getBytes(StandardCharsets.UTF_8), password, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decryptAES(byte[] content, String password) {
        return initAES(content, password, Cipher.DECRYPT_MODE);
    }

    private static byte[] initAES(byte[] content, String password, int mode) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(256, new SecureRandom(password.getBytes()));
            byte[] enCodeFormat = keyGenerator.generateKey().getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(mode, key);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String encodeSha256Str(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            return byte2hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("sha256Encode exception ", e);
            return null;
        }
    }

    /**
     * 加盐的md5签名
     * @param source 需要签名的值
     * @param salt 盐值
     * @return 加盐后的结果
     */
    public static String encodeMd5(String source, String salt) {
        return encodeMd5(salt+source + salt);
    }

    /**
     * 获取md5签名
     * @param source 对内容进行编码
     * @return
     */
    public static String encodeMd5(String source) {
        try {
            return byte2hex(MessageDigest.getInstance(MD5).digest(source.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not found");
        }
    }

    /**
     * 字节数组转换为十六进制
     * @param bytes 需要转换的字节
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制转换为字节数组
     * @param hexStr 内容
     * @return
     */
    public static byte[] hex2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String byte2Str(byte[] source){
        return new String(source,StandardCharsets.UTF_8);
    }

    public static byte[] str2Byte(String source){
        return source.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 生成随机密码
     * 取值在26个大小写字母和数字0~9之间
     * @param size
     * 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String getRandomCode(int size) {
        final int maxNum = 62;
        // 生成的随机数
        int i;
        // 生成的密码的长度
        int count = 0;
        char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuilder pwd = new StringBuilder();
        Random r = new SecureRandom();
        while (count < size) {
            // 生成随机数，取绝对值，防止生成负数，
            // 生成的数最大为62-1
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
    /**
     * 生成随机密码
     * 取值在数字0~9之间
     * @param size
     * 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int size) {
        final int maxNum = 10;
        int i;
        int count = 0;
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuilder pwd = new StringBuilder();
        Random r = new SecureRandom();
        while (count < size) {
            // 生成随机数，取绝对值，防止生成负数，
            // 生成的数最大为62-1
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
    
}
