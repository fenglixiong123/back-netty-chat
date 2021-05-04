package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.CustomAccessDeniedHandler;
import com.flx.netty.chat.security.handler.CustomOAuth2AuthenticationEntryPoint;
import com.flx.netty.chat.security.property.CustomSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 15:42
 * @Description: 配置资源服务,微服务本身就是资源服务
 */
@Slf4j
@Configuration
@EnableResourceServer //开启了Resource Server功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启了方法级别的保护
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {


    @Resource//自定义授权认证异常处理
    private CustomOAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint;
    @Resource//自定义权限异常处理
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Resource//权限控制的配置属性
    private CustomSecurityProperties securityProperties;

    /**
     * 资源服务中，token的存储方式（只有jwt方式的时候，才需要配置）
     * @author FYK
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    /**
     * jwt中，token的编码
     * @author FYK
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        String storeWay = securityProperties.getTokenStore();
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if ("jwt".equalsIgnoreCase(storeWay)) {// 如果是jwt，对称加密
            converter.setSigningKey("abcdef");
        }
        return converter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String storeWay = securityProperties.getTokenStore();
        if("jwt".equals(storeWay)){
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(this.tokenStore());
            resources.tokenServices(defaultTokenServices);
        }else {
            resources.authenticationEntryPoint(oAuth2AuthenticationEntryPoint)//自定义授权认证异常处理
                    .accessDeniedHandler(accessDeniedHandler);//自定义权限异常处理
        }
    }

    /**
     * 配置资源访问规则
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //所有资源可以访问
        if(securityProperties.isPermitAll()){
            http.authorizeRequests().anyRequest().permitAll();
        }else {
            http.authorizeRequests()
                    //允许一些资源可以访问
                    .antMatchers(securityProperties.getWhiteResources()).permitAll()
                    //允许一些URL可以访问
                    .antMatchers(securityProperties.getWhitePermits()).permitAll()
                    //禁用跨站请求伪造
                    .and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                    //设置一个拒绝访问的提示
                    //.and().exceptionHandling().accessDeniedPage("/permitNone.html")
                    .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                    //剩下的必须经过授权访问
                    .and().authorizeRequests().anyRequest().authenticated();
        }

    }

}
