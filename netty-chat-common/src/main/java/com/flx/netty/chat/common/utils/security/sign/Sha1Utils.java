package com.flx.netty.chat.common.utils.security.sign;

import com.flx.netty.chat.common.utils.security.ByteUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 18:38
 * @Description:
 *
 * 跟md5一样是消息摘要算法啊，比md5更加安全，会产生160位的消息摘要
 *
 * 应用在检查文件报文的完整性，防篡改以及数字签名等场景
 *
 * 特性是不可逆，不能够被解密
 *
 */
public class Sha1Utils {

    /**
     * 获取md5编码后的内容
     * @param source
     * @return
     */
    public static String getSha1(String source) {
        return ByteUtils.byte2hex(sha1Digest(source.getBytes(StandardCharsets.UTF_8)));
    }

    public static String getSha1(byte[] source) {
        return ByteUtils.byte2hex(sha1Digest(source));
    }

    /**
     * 128(16字节)位的md5签名
     */
    private static byte[] sha1Digest(byte[] bytes) {
        try {
            return MessageDigest.getInstance("sha1").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA1 algorithm not found");
        }
    }

}
