package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.security.property.SecurityClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(value = SecurityClientProperties.class)
public class AppBeanConfiguration {

    @Bean
    public AuthenticationDeniedHandler authenticationDeniedHandler(){
        return new AuthenticationDeniedHandler();
    }
    /**
     * 权限不足处理方式
     * @return
     */
    @Bean
    public PermissionDeniedHandler permissionDeniedHandler(){
        return new PermissionDeniedHandler();
    }

}
