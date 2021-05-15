package com.flx.netty.chat.gateway.utils;

/**
 * @Author Fenglixiong
 * @Create 2021/5/15 3:43
 * @Description
 **/
public class FormatUtils {

    /**
     * 将字符串用中括号括起来
     * @param s 字符串
     * @return [s]
     */
    static String wrapStringWithBracket(String s) {
        return "[" + s + "] ";
    }

}
