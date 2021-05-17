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
import org.springframework.security.access.vote.UnanimousBased;

import java.util.ArrayList;
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
        //new AuthenticatedVoter() new RoleVoter()
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(accessDecisionVoter());
        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
        affirmativeBased.setAllowIfAllAbstainDecisions(true);
        return affirmativeBased;
    }

}
