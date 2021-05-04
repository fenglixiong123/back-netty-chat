package com.flx.netty.chat.login.console.security.config;

import com.flx.netty.chat.login.console.security.client.CustomClientDetailsService;
import com.flx.netty.chat.login.console.security.token.info.CustomTokenEnhancer;
import com.flx.netty.chat.login.console.security.token.jwt.CustomJwtAccessTokenConverter;
import com.flx.netty.chat.login.console.security.token.store.CustomTokenStore;
import com.flx.netty.chat.login.console.security.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 11:38
 * @Description: 授权认证服务配置，主要跟其他客户端访问我们这个sso服务中心有关
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired//Token存储信息(客户端获取token的地方)
    private CustomTokenStore tokenStore;
    @Autowired//Token信息增强
    private CustomTokenEnhancer tokenEnhancer;
    @Autowired//授权信息管理(用户的授权信息)
    private AuthenticationManager authenticationManager;
    @Autowired//用户信息服务
    private CustomUserDetailsService userDetailsService;
    @Autowired//资源客户端信息(各个微服务)
    private CustomClientDetailsService clientDetailsService;

    /**
     * 配置客户端信息
     * 客户端的配置信息既可以放在内存中，也可以放在数据库中，也可以是直接搞的一些策略。
     * 客户端在获取token的时候，会给你个clientId，然后根据这个clientId返回ClientDetails
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }


    /**
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");//获取token不需要验证
        security .checkTokenAccess("isAuthenticated()");//检查token需要事先登录
        security.allowFormAuthenticationForClients();//允许表单认证
    }



    /**
     * 配置授权Token的节点和Token服务
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        endpoints
                .tokenStore(tokenStore.getTokenStore())//token存储方式
                .tokenEnhancer(tokenEnhancer)//token信息增强
                .userDetailsService(userDetailsService)//用户详细信息数据
                .authenticationManager(authenticationManager)//启用auth2管理用户信息
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }
}
