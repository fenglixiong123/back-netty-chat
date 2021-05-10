package com.flx.netty.chat.security.property;

import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@Slf4j
//不知道为什么打成jar包就不生效，但是可以读取application.properties中的配置
//@PropertySource(value = "classpath:/security-resource.properties",ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "flx.security")
public class CustomSecurityProperties {

    /**
     * 所有资源可以访问
     */
    private boolean permitAll = false;

    /**
     * 白名单路径url
     */
    private List<String> whitePermits;

    /**
     * 白名单资源resource
     */
    private List<String> whiteResources;

    /**
     * token的存储方式
     */
    private String tokenStore;

    /**
     * list转换成不重复的数组
     * @param source
     * @return
     */
    public static String[] list2Array(List<String> source){
        if(CollectionUtils.isNotEmpty(source)){
            Set<String> whiteSet = new HashSet<>();
            for (String item : source){
                whiteSet.addAll(Arrays.asList(item.split(",")));
            }
            return whiteSet.toArray(new String[source.size()]);
        }else {
            return new String[]{};
        }
    }


}
