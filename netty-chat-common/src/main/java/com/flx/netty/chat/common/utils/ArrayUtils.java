package com.flx.netty.chat.common.utils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 15:35
 * @Description:
 */
public class ArrayUtils {

    public static boolean isNull(Object[] array){
        if(array==null || array.length==0){
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object[] array){
        if(array!=null && array.length>0){
            return true;
        }
        return false;
    }

}
