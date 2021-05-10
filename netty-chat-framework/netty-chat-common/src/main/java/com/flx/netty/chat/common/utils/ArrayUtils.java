package com.flx.netty.chat.common.utils;

import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 15:35
 * @Description:
 */
public class ArrayUtils {

    public final static String DEFAULT_SPLIT = ",";

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

    public static String[] list2Array(List<String> source){
        return list2Array(source,DEFAULT_SPLIT,true);
    }
    /**
     * list转换成不重复的数组
     * @param source
     * @return
     */
    public static String[] list2Array(List<String> source,String split,boolean unique){
        if(StringUtils.isBlank(split)){
            split = DEFAULT_SPLIT;
        }
        if(unique) {
            Set<String> whiteSet = new HashSet<>();
            if (CollectionUtils.isNotEmpty(source)) {
                for (String item : source) {
                    whiteSet.addAll(Arrays.asList(item.split(split)));
                }
            }
            return whiteSet.toArray(new String[source.size()]);
        }else {
            return source.toArray(new String[0]);
        }
    }

}
