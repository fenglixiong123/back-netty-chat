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
 **/
@Slf4j
public class PropertyUtils extends SystemPropertyUtils {

    public static final String defaultLocation = "application.properties";

    /**
     * 获取配置项
     * @param key spring.name
     * @return
     */
    public static String getProperty(String key){
        log.info("Ready to load resource : isClassPath = {} , location = {} , key = {}",true,defaultLocation,key);
        return Objects.requireNonNull(getPropertyEntity(defaultLocation,true)).getProperty(key);
    }

    /**
     * 根据文件获取配置项
     * @param location application.properties
     * @param key spring.name
     * @return
     */
    public static String getProperty(String location,String key){
        log.info("Ready to load resource : isClassPath = {} , location = {} , key = {}",true,location,key);
        return Objects.requireNonNull(getPropertyEntity(location,true)).getProperty(key);
    }

    /**
     * 获取配置项存放在指定路径下
     * @param location C:\soft\application.yml
     * @param key
     * @return
     */
    public static String getFileProperty(String location,String key){
        log.info("Ready to load resource : isClassPath = {} , location = {} , key = {}",false,location,key);
        return Objects.requireNonNull(getPropertyEntity(location,false)).getProperty(key);
    }

    public static Properties getPropertyEntity(String location,boolean isClassPath){
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
     * @param resource
     * @return
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

    private PropertyUtils(){
        //Unsafe
    }

}
