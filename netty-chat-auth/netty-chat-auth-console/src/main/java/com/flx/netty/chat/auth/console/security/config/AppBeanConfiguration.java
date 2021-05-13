package com.flx.netty.chat.auth.console.security.config;

import com.flx.netty.chat.auth.console.security.client.CustomClientDetailsService;
import com.flx.netty.chat.auth.console.security.token.info.CustomTokenEnhancer;
import com.flx.netty.chat.auth.console.security.token.store.CustomTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired//Token存储信息
    private CustomTokenStore customTokenStore;
    @Autowired//Token信息增强
    private CustomTokenEnhancer tokenEnhancer;
    @Autowired//资源客户端信息(各个微服务)
    private CustomClientDetailsService clientDetailsService;

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
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());//设置token存储方式
        tokenServices.setTokenEnhancer(tokenEnhancer);//设置token附加信息
        tokenServices.setClientDetailsService(clientDetailsService);//设置这个如果客户端信息配置了默认的会失效
        tokenServices.setSupportRefreshToken(true);//默认设置支持token刷新
        tokenServices.setReuseRefreshToken(true);//默认设置支持token重用
        tokenServices.setAccessTokenValiditySeconds(7200);//设置默认token过期时间
        tokenServices.setRefreshTokenValiditySeconds(7200);//设置默认token刷新过期时间
        return tokenServices;
    }

}
