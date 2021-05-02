package com.flx.netty.chat.plugin.utils;

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
import java.util.Random;


/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 12:44
 * @Description:
 */
@Slf4j
public class CryptoTool {

    private CryptoTool() {
    }

    private static class CryptoToolHolder {
        private static final CryptoTool INSTANCE = new CryptoTool();
    }

    public static CryptoTool getCrypto() {
        return CryptoToolHolder.INSTANCE;
    }

    public String encode64(String src) {
        return Base64.getEncoder().encodeToString(src.getBytes(StandardCharsets.UTF_8));
    }


    public String encode64(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public String decode64(String src) {
        byte[] asBytes = Base64.getDecoder().decode(src);
        return new String(asBytes, StandardCharsets.UTF_8);
    }

    public String decode64(String src,String charset){
        byte[] asBytes = Base64.getMimeDecoder().decode(src);
        String target = null;
        try {
            if ("".equals(charset)) {
                target = new String(asBytes);
            }else {
                target = new String(asBytes,charset);
            }
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
        }
        return target;
    }


    public String encryptAES2Str(String content, String secret) {
        byte[] bytes = encryptAES(content, secret);
        return byte2Hex(bytes);
    }

    public String decryptAES2Str(String encryptContent, String secret) {
        byte[] bytes = decryptAES(hex2Byte(encryptContent), secret);
        String target = null;
        target = new String(bytes, StandardCharsets.UTF_8);
        return target;
    }

    public byte[] encryptAES(String content, String secret) {
        return encryptAES(content.getBytes(StandardCharsets.UTF_8), secret, Cipher.ENCRYPT_MODE);
    }


    public byte[] decryptAES(byte[] content, String secret) {
        return encryptAES(content, secret, Cipher.DECRYPT_MODE);
    }

    public byte[] encryptAES(byte[] content, String password, int mode) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //SecureRandom  类中 setSeed()底层调用的是 native 方法.所以 造成了 不同环境之间 随机数出现了差别
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(256, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, key);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
        }
        return new byte[0];
    }

    public String encodeSha256Str(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            return byte2Hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("sha256Encode exception ", e);
            return null;
        }
    }

    public String md5(String key, String salt) {
        return md5(key + salt);
    }

    public String md5(String key) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = key.getBytes(StandardCharsets.UTF_8);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private String byte2Hex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public byte[] hex2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return new byte[0];
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 生成随机密码
     *
     * @param size
     * 生成的密码的总长度
     * @return 密码的字符串
     */
    public String getRandomCode(int size) {
        // 26*2个字母+10个数字
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
     *
     * @param size
     * 生成的密码的总长度
     * @return 密码的字符串
     */
    public String genRandomNum(int size) {
        // 10个数字
        final int maxNum = 10;
        // 生成的随机数
        int i;
        // 生成的密码的长度
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
