package com.flx.netty.chat.admin.common.security.config;

import com.flx.netty.chat.admin.common.security.permission.SystemAccessDecisionVoter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/25 16:03
 * @Description:
 */
@Slf4j
@Configuration
public class BeanConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 表达式权限投票器
     * @return
     */
    @Bean
    public WebExpressionVoter webExpressionVoter(){
        return new WebExpressionVoter();
    }

    /**
     * 自定义权限投票器
     */
    @Bean
    public SystemAccessDecisionVoter accessDecisionVoter(){
        return new SystemAccessDecisionVoter();
    }

    /**
     * FilterSecurityInterceptor
     *   |--->UnanimousBased
     *      |--->WebExpressionVoter
     *      |--->CustomAccessDecisionVoter
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
