package com.flx.netty.chat.login.console.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@Component
@PropertySource(value = "classpath:/security.properties")
@ConfigurationProperties(prefix = "flx.security")
public class CustomSecurityProperties {

    /**
     * 白名单路径url
     */
    private String[] whitePermits;

    /**
     * 白名单资源resource
     */
    private String[] whiteResources;

    /**
     * token的存储方式
     */
    private String tokenStore;

    /**
     * jwt签名秘钥，仅当启用jwt的token方式时候起作用
     */
    private String signingKey;

}
