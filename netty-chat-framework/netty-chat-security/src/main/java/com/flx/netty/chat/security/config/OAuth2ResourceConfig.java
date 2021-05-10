package com.flx.netty.chat.security.config;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.security.property.CustomSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 15:42
 * @Description: 配置资源服务,微服务本身就是资源服务
 * ResourceServerConfig是比SecurityConfig的优先级低的
 */
@Slf4j
@Configuration
@EnableResourceServer //开启了Resource Server功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启了方法级别的保护
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {


    @Resource(name = "resourceAuthenticationDeniedHandler")//自定义授权认证异常处理
    private AuthenticationDeniedHandler oAuth2AuthenticationEntryPoint;
    @Resource(name = "resourcePermissionDeniedHandler")//自定义权限异常处理
    private PermissionDeniedHandler accessDeniedHandler;
    @Autowired//权限控制的配置属性
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
            List<String> whitePermits = securityProperties.getWhitePermits();
            List<String> whiteResources = securityProperties.getWhiteResources();
            String[] permits = CustomSecurityProperties.list2Array(whitePermits);
            String[] resources = CustomSecurityProperties.list2Array(whiteResources);
            log.info("========>whitePermits = {}", JsonUtils.toJsonMsg(permits));
            log.info("========>whiteResources = {}", JsonUtils.toJsonMsg(resources));
            http
                //设置一个拒绝访问的提示
                .csrf().disable().exceptionHandling()
                    .authenticationEntryPoint(oAuth2AuthenticationEntryPoint)//自定义授权认证异常处理
                    .accessDeniedHandler(accessDeniedHandler)//自定义权限异常处理
                .and()
                    .authorizeRequests()//需要授权的访问地址
                        .antMatchers(permits).permitAll()//允许一些URL可以访问
                        .antMatchers(resources).permitAll()//允许一些资源可以访问
                //剩下的必须经过授权访问
                .and()
                    .authorizeRequests()
                        .anyRequest().authenticated();
        }

    }

}
