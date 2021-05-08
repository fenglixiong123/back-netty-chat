package com.flx.netty.chat.auth.console.security.config;

import com.flx.netty.chat.auth.console.security.token.info.CustomTokenEnhancer;
import com.flx.netty.chat.auth.console.security.token.store.CustomTokenStore;
import com.flx.netty.chat.auth.console.security.client.CustomClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.sql.DataSource;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 11:38
 * @Description: 授权认证服务配置，主要跟其他客户端访问我们这个sso服务中心有关
 */
@Configuration
@EnableAuthorizationServer //当前应用是一个认证服务器
public class Auth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired//数据源
    private DataSource dataSource;
    @Autowired//Token存储信息(客户端获取token的地方)
    private CustomTokenStore tokenStore;
    @Autowired//Token信息增强
    private CustomTokenEnhancer tokenEnhancer;
    @Autowired//授权信息管理(用户的授权信息)
    private AuthenticationManager authenticationManager;
    @Autowired//资源客户端信息(各个微服务)
    private CustomClientDetailsService clientDetailsService;

    /**
     * 配置客户端应用的信息
     *
     * 告诉认证服务器知道有哪些客户端应用来申请令牌。
     *
     *  微服务中最起码两个客户端，一个是你的前端app来申请token令牌的，另一个是用户要访问的微服务订单系统
     *  前端app将申请到的token带到微服务订单系统中，订单系统开始向认证服务验证token是否有效
     *
     * 客户端的配置信息既可以放在内存中，也可以放在数据库中，也可以是直接搞的一些策略。
     * 客户端在获取token的时候，会给你个clientId，然后根据这个clientId返回ClientDetails
     *
     * clientId //客户端id
     * secret //客户端秘钥
     * scope //客户端权限范围get/post
     * authorizedGrantTypes //客户端可以发起的授权类型
     *
     * @param clients 客户端的详情服务的配置
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /*
        //内存的方式
        clients.inMemory()//配置在内存里，后面修改为数据库里
                //~============== 注册【客户端应用】使客户端应用能够访问认证服务器，获得令牌 ===========
                .withClient("netty-chat-front")
                .secret("123456") //正常需要加密,aes加密
                .scopes("read","write") //前端app有哪些权限，读(get)写(post)权限
                .accessTokenValiditySeconds(3600) //token的有效期
                .resourceIds("netty-chat-message") //资源服务器的id。获得的token，能访问哪些资源服务器，可以多个
                .authorizedGrantTypes("authorization_code","password","refresh_token","client_credentials")//授权方式，此客户端可以使用的授权方式
                //~=============客户端应用配置结束 =====================
                .and()
                //~============== 注册【资源服务器-消息服务】使消息服务也能够访问认证服务器，验证令牌 ===========
                .withClient("netty-chat-message")
                .secret("123456") //生产需要加密处理
                .scopes("read","write") //有哪些权限，读(get)写(post)权限
                .accessTokenValiditySeconds(3600) //token的有效期
                .resourceIds("") //资源服务器的id
                .authorizedGrantTypes("password");//授权方式
        */
        //使用内存模式将客户端信息存入数据库中
        //下面是自带的，不如自己来进行数据库管理
        //表为：oauth_client_details
        //JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        //clients.withClientDetails(clientDetailsService);

        //自定义数据库管理
        clients.withClientDetails(clientDetailsService);

    }

    /**
     * 配置用户信息
     *
     * 告诉认证服务器，有哪些用户可以来访问认证服务器
     *
     * 配置授权Token的节点和Token服务
     *
     * authenticationManager //用户信息校验管理器(主要验证用户信息)
     * authorizationCodeService //授权码模式的配置，可以将授权码配置为数据库存储
     * tokenStore //token的存储方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        endpoints
                .tokenStore(tokenStore.getTokenStore())//token存储方式
                .tokenEnhancer(tokenEnhancer)//token信息增强
                .authenticationManager(authenticationManager)//用来校验传过来的用户信息是不是合法的
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }


    /**
     * 配置资源服务器过来验token的规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //security.tokenKeyAccess("permitAll()");//获取token不需要验证
        //security .checkTokenAccess("isAuthenticated()");//检查token需要事先登录
        security.allowFormAuthenticationForClients();//允许postman表单认证
    }




}
