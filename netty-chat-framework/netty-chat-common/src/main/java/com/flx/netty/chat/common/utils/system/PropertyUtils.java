package com.flx.netty.chat.common.utils.system;

import com.flx.netty.chat.common.constants.WebConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Objects;
import java.util.Properties;

/**
 * @Author Fenglixiong
 * @Create 2020/8/4 1:30
 * @Description
 *
 * contains(String key) //是否包含某个属性
 * get(String key) //获取字符串属性
 * get(final String key, String def) //获取属性找不到则给默认值
 * getBoolean(String key, boolean def) //获取boolean属性
 * getInt(String key, int def) //获取int属性
 * getLong(String key, int def) //获取Long属性
 *
 **/
@Slf4j
public class PropertyUtils {

    private static final String defaultLocation = "application.properties";


    public static boolean isExist(String key) {
        return get(key) != null;
    }

    public static Integer getIntegerOrError(String key) throws Exception{
        return getIntegerFromLocationOrError(defaultLocation,key);
    }

    public static int getInteger(String key, int def) {
        return getIntegerFromLocation(defaultLocation,key,def);
    }

    public static Integer getInteger(String key) {
       return getIntegerFromLocation(defaultLocation,key);
    }

    public static Integer getIntegerFromLocationOrError(String location,String key) throws Exception{
        String value = getFromLocation(location,key);
        if (value == null) {
            throw new Exception("Get Integer error !");
        }
        value = value.trim();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Get Integer error !");
    }

    public static int getIntegerFromLocation(String location,String key, int def) {
        String value = getFromLocation(location,key);
        if (value == null) {
            return def;
        }
        value = value.trim();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static Integer getIntegerFromLocation(String location,String key) {
        String value = getFromLocation(location,key);
        if (value == null) {
            return null;
        }
        value = value.trim();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLongOrError(String key) throws Exception{
        return getLongFromLocationOrError(defaultLocation,key);
    }

    public static long getLong(String key, long def) {
        return getLongFromLocation(defaultLocation,key,def);
    }

    public static Long getLong(String key) {
        return getLongFromLocation(defaultLocation,key);
    }

    public static long getLongFromLocationOrError(String location,String key) throws Exception{
        String value = getFromLocation(location,key);
        if (value == null) {
            throw new Exception("Get Long error !");
        }
        value = value.trim();
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Get Long error !");
    }

    public static long getLongFromLocation(String location,String key, long def) {
        String value = getFromLocation(location,key);
        if (value == null) {
            return def;
        }
        value = value.trim();
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static Long getLongFromLocation(String location,String key) {
        String value = getFromLocation(location,key);
        if (value == null) {
            return null;
        }
        value = value.trim();
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getBooleanOrError(String key)throws Exception{
        return getBooleanFromLocationOrError(defaultLocation,key);
    }

    public static boolean getBoolean(String key,boolean def){
        return getBooleanFromLocation(defaultLocation,key,def);
    }

    public static Boolean getBoolean(String key){
        return getBooleanFromLocation(defaultLocation,key);
    }

    public static boolean getBooleanFromLocationOrError(String location,String key) throws Exception{
        Boolean value = getBooleanFromLocation(location,key);
        if(value==null){
            throw new Exception("Get boolean error !");
        }
        return value;
    }

    public static boolean getBooleanFromLocation(String location,String key,boolean def){
        Boolean value = getBooleanFromLocation(location,key);
        if(value==null){
            return def;
        }
        return value;
    }

    public static Boolean getBooleanFromLocation(String location,String key){
        String value = getFromLocation(location,key);
        if (value==null){
            return null;
        }

        value = value.trim().toLowerCase();
        if (value.isEmpty()) {
            return null;
        }

        if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
            return true;
        }

        if ("false".equals(value) || "no".equals(value) || "0".equals(value)) {
            return false;
        }

        return null;
    }

    public static String getOrError(String key)throws Exception{
        return getFromLocationOrError(defaultLocation,key);
    }

    public static String get(String key,String def){
        return getFromLocation(defaultLocation,key,def);
    }

    public static String get(String key){
        return getFromLocation(defaultLocation,key);
    }

    public static String getFromLocationOrError(String location,String key)throws Exception{
        String value = getFromLocation(location,key);
        if(value==null){
            throw new Exception("Get key error !");
        }
        return value;
    }

    public static String getFromLocation(String location,String key,String def){
        String value = getFromLocation(location,key);
        if (value==null){
            return def;
        }
        return value;
    }

    /**
     * 根据文件获取配置项
     * @param location application.properties
     * @param key spring.name
     * @return 配置项对应的值
     */
    public static String getFromLocation(String location,String key){

        if (key == null) {
            throw new NullPointerException("key");
        }

        if (key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty.");
        }

        return Objects.requireNonNull(getPropertyEntity(location,true)).getProperty(key);
    }

    /**
     * 获取配置项存放在指定路径下
     * @param path C:\soft\application.yml
     * @param key 配置项名称
     * @return 配置项对应的值
     */
    public static String getFromFile(String path,String key){
        log.info("Ready to load resource : isClassPath = {} , location = {} , key = {}",false,path,key);
        return Objects.requireNonNull(getPropertyEntity(path,false)).getProperty(key);
    }

    private static Properties getPropertyEntity(String location,boolean isClassPath){
        if(StringUtils.isBlank(location)){
            location = "application.properties";
        }
        try{
            if(isClassPath) {
                return PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(location), WebConstant.UTF_8));
            }else {
                return PropertiesLoaderUtils.loadProperties(new EncodedResource(new FileSystemResource(location), WebConstant.UTF_8));
            }
        }catch (Exception e){
            log.info("Load resource failed , location = {}",location);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取配置文件类
     * @param resource 资源文件
     * @return 配置属性
     */
    public static Properties getPropertyEntity(Resource resource){
        if(resource==null){
            return null;
        }
        String location = null;
        try{
            if(resource.isFile()) {
                location = resource.getFilename();
                log.info("Ready to load resource : fileName = {}", resource.getFilename());
            }else {
                location = resource.getURL().toString();
                log.info("Ready to load resource : url = {}", resource.getURL());
            }
            return PropertiesLoaderUtils.loadProperties(resource);
        }catch (Exception e){
            log.info("Load resource failed , location = {}",location);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 禁止实例化
     */
    private PropertyUtils(){
        //Unsafe
    }

}
