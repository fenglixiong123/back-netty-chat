package com.flx.netty.chat.auth.console.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description: 注入一些常用的bean
 */
@Configuration
public class AppConfiguration {

    /**
     * 权限不足处理方式
     * @return
     */
    @Bean
    public JwtAccessTokenConverter customDeniedHandler(){
        return new JwtAccessTokenConverter();
    }

}
