package com.flx.netty.chat.generator.utils.property.simple;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/25 15:11
 * @Description:
 */
public class PropertyUtils {

    private static final String CONFIG_FILE = "application-simple.properties";

    public static String get(String key){
        return com.flx.netty.chat.common.utils.system.PropertyUtils.getFromLocation(CONFIG_FILE,key);
    }

    public static String get(String key,String def){
        return com.flx.netty.chat.common.utils.system.PropertyUtils.getFromLocation(CONFIG_FILE,key,def);
    }

    public static boolean getBoolean(String key,boolean def){
        return com.flx.netty.chat.common.utils.system.PropertyUtils.getBooleanFromLocation(CONFIG_FILE,key,def);
    }

}
