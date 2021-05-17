package com.flx.netty.chat.security.config;

import com.flx.netty.chat.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.security.permiss.CustomAccessDecisionVoter;
import com.flx.netty.chat.security.property.SecurityResourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 17:24
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(value = SecurityResourceProperties.class)
public class AppBeanConfiguration {

    @Bean(value = "resourceAuthenticationDeniedHandler")
    public AuthenticationDeniedHandler authenticationDeniedHandler(){
        return new AuthenticationDeniedHandler();
    }
    /**
     * 权限不足处理方式
     * @return
     */
    @Bean(value = "resourcePermissionDeniedHandler")
    public PermissionDeniedHandler permissionDeniedHandler(){
        return new PermissionDeniedHandler();
    }


//    /**
//     * 自定义权限投票器
//     */
//    @Bean
//    public CustomAccessDecisionVoter accessDecisionVoter(){
//        return new CustomAccessDecisionVoter();
//    }

    /**
     * 访问权限决策器
     * AffirmativeBased     只要任一返回肯定的结果，便授予访问权限
     * ConsensusBased       少数服从多数授权访问决策方案
     * UnanimousBased       所有同意才能代表授予权限
     */
//    @Bean
    public AccessDecisionManager accessDecisionManager() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(new OAuth2WebSecurityExpressionHandler());
        //new AuthenticatedVoter() new RoleVoter()
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter,//Web表达式决策器,目的让我们设置的http决策也成功，而且排在第一，然后才是我们自定义的决策器生效
                //new RoleVoter(),//角色决策器
                //new AuthenticatedVoter(),//身份验证决策器
                new CustomAccessDecisionVoter());//自定义权限决策器
        return new AffirmativeBased(decisionVoters);
    }

}
