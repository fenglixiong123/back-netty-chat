package com.flx.netty.chat.common.utils.security.codec;

import com.flx.netty.chat.common.utils.ArrayUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 20:25
 * @Description:
 */
public class Base64Utils {

    public static void main(String[] args) {
        String a = "Hello,123";
        String b = encodeToStr(a);
        String c = decodeToStr(b);
        System.out.println(c);
    }

    /**
     * 编码
     *
     * @param source
     */
    public static byte[] encode(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return Base64.encodeBase64(source.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encode(byte[] source) {
        if (ArrayUtils.isNull(source)) {
            return null;
        }
        return Base64.encodeBase64(source);
    }

    public static byte[] decode(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return Base64.decodeBase64(source);
    }

    public static byte[] decode(byte[] source) {
        return Base64.decodeBase64(source);
    }

    public static String encodeToStr(String source) {
        return byte2Str(encode(source));
    }

    public static String encodeToStr(byte[] source) {
        return byte2Str(encode(source));
    }

    public static String decodeToStr(String source) {
        return byte2Str(decode(source));
    }

    public static String decodeToStr(byte[] source) {
        return byte2Str(decode(source));
    }

    /**
     * 判断是否base64过的原文，原理为解密再加密，得到原字符串则代表的是已加密过的
     */
    public static boolean isBase64(final String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }
        String de = decodeToStr(source);
        String en = Objects.requireNonNull(encodeToStr(de)).replaceAll("[\\s*\t\n\r]", "");
        return source.equals(en);
    }

    /**
     * 得到一个base64文件的实际大小
     */
    public static long base64Ssize(String source) {
        //1.获取base64字符串长度(不含data:audio/wav;base64,文件头)
        int size = source.length();
        //2.获取字符串的尾巴的最后10个字符，用于判断尾巴是否有等号，正常生成的base64文件'等号'不会超过4个
        String tail = source.substring(size - 10);
        //3.找到等号，把等号也去掉,(等号其实是空的意思,不能算在文件大小里面)
        int equalIndex = tail.indexOf("=");
        if (equalIndex > 0) {
            size = size - (10 - equalIndex);
        }
        //4.计算后得到的文件流大小，单位为字节
        return size - ((long) size / 8) * 2;

    }

    public static String byte2Str(byte[] source){
        return new String(source,StandardCharsets.UTF_8);
    }

}
