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
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
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

    /**
     * 表达式权限投票器
     * @return
     */
    @Bean
    public WebExpressionVoter webExpressionVoter(){
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(new OAuth2WebSecurityExpressionHandler());
        return webExpressionVoter;
    }

    /**
     * 自定义权限投票器
     */
    @Bean
    public CustomAccessDecisionVoter accessDecisionVoter(){
        return new CustomAccessDecisionVoter();
    }

    /**
     * 访问权限决策器
     * AffirmativeBased     只要任一返回肯定的结果，便授予访问权限
     * ConsensusBased       少数服从多数授权访问决策方案
     * UnanimousBased       所有同意才能代表授予权限
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),//主要用来验证是否登录
                accessDecisionVoter());//主要用来验证请求权限
        return new UnanimousBased(decisionVoters);//上面必须同时满足才能放行
    }

}
