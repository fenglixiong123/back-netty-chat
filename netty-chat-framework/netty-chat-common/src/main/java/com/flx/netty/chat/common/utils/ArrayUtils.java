package com.flx.netty.chat.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 15:35
 * @Description:
 */
public class ArrayUtils {

    public static boolean isNull(byte[] array){
        if(array==null || array.length==0){
            return true;
        }
        return false;
    }

    public static boolean isNotNull(byte[] array){
        return !isNull(array);
    }

    public static boolean isNull(Object[] array){
        if(array==null || array.length==0){
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object[] array){
        return !isNull(array);
    }

    public static <T> List<T> asList(T ...t){
        return Arrays.asList(t);
    }

}
