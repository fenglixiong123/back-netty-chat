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
        System.out.println(byte2Str(hex2Byte("68656C6C6F2C66656E676C6978696F6E67")));
    }


    /**
     * 字节转换成16进制
     * 推荐，用时1毫秒
     * 等同于：String.format("%032x", new BigInteger(1, bytes))
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

    /**将16进制转换为二进制
     * @param hexStr
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

}
