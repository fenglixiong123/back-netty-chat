package com.flx.netty.chat.security.property;

import com.flx.netty.chat.common.utils.CollectionUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/security-resource.properties")
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
        Set<String> whiteSet = new HashSet<>();
        if(CollectionUtils.isNotEmpty(source)){
            for (String item : source){
                whiteSet.addAll(Arrays.asList(item.split(",")));
            }
        }
        return whiteSet.toArray(new String[source.size()]);
    }

}
