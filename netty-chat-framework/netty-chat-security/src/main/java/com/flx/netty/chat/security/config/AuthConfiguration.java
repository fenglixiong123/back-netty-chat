package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.CustomDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description:
 */
@Configuration
public class AuthConfiguration {

    /**
     * 权限不足处理方式
     * @return
     */
    @Bean
    public CustomDeniedHandler customDeniedHandler(){
        return new CustomDeniedHandler();
    }

}
