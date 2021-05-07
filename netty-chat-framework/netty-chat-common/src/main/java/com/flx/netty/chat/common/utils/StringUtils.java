package com.flx.netty.chat.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/7 18:56
 * @Description:
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private final static String DEFAULT_DELIMITER = ",";

    public static List<String> toList(String source){
        return toList(source,DEFAULT_DELIMITER);
    }

    /**
     * 字符串转换成列表
     */
    public static List<String> toList(String source, String delimiter){
        if(isBlank(source)){
            return null;
        }
        return Arrays.asList(source.split(delimiter));
    }

    public static Set<String> toSet(String source){
        return toSet(source,DEFAULT_DELIMITER);
    }

    /**
     * 字符串转换成set
     */
    public static Set<String> toSet(String source,String delimiter){
        if(isBlank(source)){
            return null;
        }
        return new HashSet<>(Arrays.asList(source.split(delimiter)));
    }

    /**
     * 字符串转换成Map
     */
    public static <T> Map<String,T> toMap(String source){
        if(isBlank(source)){
            return null;
        }
        return JSON.parseObject(source,new TypeReference<HashMap<String,T>>(){});
    }

}
