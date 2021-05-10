package com.flx.netty.chat.security.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
public class SecurityClientProperties {

    /**
     * Auth服务的资源ID
     */
    private String resourceId;

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


}
