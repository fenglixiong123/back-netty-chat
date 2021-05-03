package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.CustomDeniedHandler;
import com.flx.netty.chat.security.property.CustomSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

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
@EnableConfigurationProperties(value = CustomSecurityProperties.class)
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {


    @Resource
    private CustomDeniedHandler customDeniedHandler;
    @Resource
    private CustomSecurityProperties securityProperties;

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
                    .and().exceptionHandling().accessDeniedHandler(customDeniedHandler)
                    //剩下的必须经过授权访问
                    .and().authorizeRequests().anyRequest().authenticated();
        }

    }

}
