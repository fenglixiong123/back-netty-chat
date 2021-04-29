package com.flx.netty.chat.common.utils.security.sign;

import com.flx.netty.chat.common.utils.security.ByteUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.flx.netty.chat.common.constants.SecurityConstant.MD5;
import static com.flx.netty.chat.common.utils.security.codec.Base64Utils.encode;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/25 16:32
 * @Description:
 *
 * 严格来说，MD5不是一种加密算法而是摘要算法。
 *
 * MD5用的是哈希函数，它的典型应用是对一段信息产生信息摘要，以防止被篡改。
 * 无论是多长的输入，MD5都会输出长度为 128bits的一个串(通常用16进制表示为32个字符)。
 *
 */
public class Md5Utils {

    public static void main(String[] args) {
        System.out.println(getMD5("Hello"));
    }

    /**
     * 获取md5编码后的内容
     * @param source
     * @return
     */
    public static String getMD5(String source) {
        return getMD5(source.getBytes(StandardCharsets.UTF_8));
    }

    public static String getMD5(byte[] source) {
        return ByteUtils.byte2hex(digest(source));
    }

    /**
     * md5签名算法
     * Md5签名+Base64编码+转十进制
     * @param source
     * @return
     */
    public static String getBase64MD5(byte[] source) {
        return ByteUtils.byte2hex(Objects.requireNonNull(encode(digest(source))));
    }

    /**
     * 128(16字节)位的md5签名
     */
    public static byte[] digest(byte[] bytes) {
        try {
            return MessageDigest.getInstance(MD5).digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not found");
        }
    }

}
