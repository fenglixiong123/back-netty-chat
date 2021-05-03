package com.flx.netty.chat.common.utils.code;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/25 16:32
 * @Description:
 */
public class CodeUtils {

    public static void main(String[] args) {
        
    }

    //转变的依赖字符
    private static final char UNDERLINE = '_';

    /**
     * userId--->user_id
     * @param source 驼峰转换成小写下划线
     * @return
     */
    public static String camelToUnder(String source) {
        if (source == null || "".equals(source.trim())) {
            return "";
        }
        int len = source.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(Character.toLowerCase(source.charAt(0)));
        for (int i = 1; i < len; i++) {
            char c = source.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE).append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * user_id--->userId
     * @param source 下划线转驼峰
     * @return
     */
    public static String underToCamel(String source) {
        if (source == null || "".equals(source.trim())) {
            return "";
        }
        int len = source.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = source.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(source.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
