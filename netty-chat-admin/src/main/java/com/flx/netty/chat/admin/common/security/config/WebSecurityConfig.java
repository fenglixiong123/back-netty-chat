package com.flx.netty.chat.admin.common.security.config;

import com.flx.netty.chat.admin.common.security.handler.*;
import com.flx.netty.chat.admin.common.security.property.SecurityProperties;
import com.flx.netty.chat.admin.common.security.user.SystemAuthenticationProvider;
import com.flx.netty.chat.common.utils.ArrayUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

import static com.flx.netty.chat.common.utils.ArrayUtils.list2Array;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/25 17:26
 * @Description:
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级安全验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SystemAuthenticationProvider authenticationProvider;
    @Autowired
    private AuthenticationDeniedHandler authenticationDeniedHandler;
    @Autowired
    private PermissionDeniedHandler permissionDeniedHandler;
    @Autowired
    private LoginSuccessfulHandler loginSuccessfulHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LogoutSuccessfulHandler logoutSuccessfulHandler;
    @Autowired
    private AccessDecisionManager accessDecisionManager;

    /**
     * 配置用户密码验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> whitePermits = securityProperties.getWhitePermits();
        String[] permits = list2Array(whitePermits);
        log.info("========>whitePermits = {}", JsonUtils.toJsonMsg(permits));

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                //.loginPage("/login.html")
                .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                .successHandler(loginSuccessfulHandler)
                .failureHandler(loginFailureHandler)
                .permitAll();
        http.logout()
                .logoutUrl(securityProperties.getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessfulHandler)
                .permitAll();

        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//不会使用任何Session
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationDeniedHandler)//Token不正确时候处理
                .accessDeniedHandler(permissionDeniedHandler)//权限不足时候处理方式
            .and()
                .authorizeRequests()
                    .antMatchers(permits).permitAll()//只匹配/oauth/**
                    .anyRequest().authenticated()
                    .accessDecisionManager(accessDecisionManager);//匹配到的路径中/oauth/**需要登录授权

    }

    /**
     * 排除一些资源url不经过security
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> whiteResources = securityProperties.getWhiteResources();
        String[] resources = list2Array(whiteResources);
        log.info("========>whiteResources = {}", JsonUtils.toJsonMsg(resources));
        if(ArrayUtils.isNotNull(resources)){
            web.ignoring().antMatchers(resources);
        }
    }

}
