package com.flx.netty.chat.auth.console.security.config;

import com.flx.netty.chat.auth.console.security.token.store.CustomTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description: 注入一些常用的bean
 */
@Configuration
public class AppBeanConfiguration {

    @Autowired
    private CustomTokenStore customTokenStore;

    /**
     * 权限不足处理方式
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        return new JwtAccessTokenConverter();
    }

    /**
     * 将tokenStore注册成bean
     * 防止远程校验token时候出现自动选用InMemoryTokenStore方式
     */
    @Bean
    public TokenStore tokenStore(){
        return customTokenStore.getTokenStore();
    }

    @Bean
    public DefaultTokenServices tokenServices(){
        return new DefaultTokenServices();
    }

}
