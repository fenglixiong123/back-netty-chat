package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.CustomDeniedHandler;
import com.flx.netty.chat.security.property.CustomSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(value = CustomSecurityProperties.class)
public class AppConfiguration {

    /**
     * 权限不足处理方式
     * @return
     */
    @Bean
    public CustomDeniedHandler customDeniedHandler(){
        return new CustomDeniedHandler();
    }

}
