package com.flx.netty.chat.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@PropertySource(value = "classpath:/security.properties")
@ConfigurationProperties(prefix = "flx.security")
public class CustomSecurityProperties {

    /**
     * 所有资源可以访问
     */
    private boolean permitAll = true;

    /**
     * 白名单资源resource
     */
    private String[] whiteResources;

    /**
     * 白名单路径url
     */
    private String[] whitePermits;

    /**
     * token的存储方式
     */
    private String tokenStore;

}
