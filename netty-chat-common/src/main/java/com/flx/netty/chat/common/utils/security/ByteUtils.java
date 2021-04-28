package com.flx.netty.chat.common.utils.security;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 17:04
 * @Description:
 */
public class ByteUtils {

    public static void main(String[] args) {
        String source = "hello,fenglixiong";
        System.out.println(byte2hex(source.getBytes(StandardCharsets.UTF_8)));
        System.out.println(encodeHex(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 字节转换成16进制
     * 用时25毫秒
     */
    public static String encodeHex(byte[] bytes) {
        long start1 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        System.out.println("A = "+(System.currentTimeMillis()-start1));
        return sb.toString();
    }

    /**
     * 字节转换成16进制
     * 推荐，用时1毫秒
     */
    public static String byte2hex(byte[] bytes) {
        long start2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        System.out.println("B = "+(System.currentTimeMillis()-start2));
        return sb.toString();
    }

}
