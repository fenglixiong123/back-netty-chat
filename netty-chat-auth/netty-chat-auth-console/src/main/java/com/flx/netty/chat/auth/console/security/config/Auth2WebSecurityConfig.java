package com.flx.netty.chat.auth.console.security.config;

import com.flx.netty.chat.auth.console.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.auth.console.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.auth.console.security.handler.UserLogoutSuccessHandler;
import com.flx.netty.chat.auth.console.security.property.SecurityServerProperties;
import com.flx.netty.chat.auth.console.security.user.password.CustomPasswordEncoder;
import com.flx.netty.chat.auth.console.security.user.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 10:01
 * @Description: 安全认证配置，主要跟用户登录之后的验证有关
 *
 * http.permitAll与web.ignoring的区别:
 *
 * http.permitAll不会绕开springSecurity验证，相当于是允许该路径通过
 * web.ignoring是直接绕开springSecurity的所有filter，直接跳过验证
 */
@Slf4j
@Configuration
@EnableWebSecurity //开启web保护功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启在方法上的保护功能
public class Auth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailService;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;
    @Autowired
    private SecurityServerProperties securityProperties;
    @Autowired
    private UserLogoutSuccessHandler logoutSuccessHandler;
    @Resource(name = "serverAuthenticationDeniedHandler")
    private AuthenticationDeniedHandler authenticationDeniedHandler;
    @Resource(name = "serverPermissionDeniedHandler")
    private PermissionDeniedHandler permissionDeniedHandler;


    /**
     * 最终将返回值AuthenticationManager作为一个bean交由spring来管理，他会被授权服务配置类用到
     * @return
     * @throws Exception
     */
    @Override
    @Bean//注册成bean方便被Auth2使用
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 该方法就是配置一些信息来为之后构造AuthenticationManager
     * 也就是authenticationManagerBean()方法的返回值，就是使用AuthenticationManagerBuilder建造的
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)//配置获取用户信息
                .passwordEncoder(passwordEncoder);//配置密码加密方式,添加用户加密的时候请也用这个加密
    }


    /**
     * 配置资源访问是否需要认证
     * 以及其他一些安全的配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationDeniedHandler)//Token不正确时候处理
                    .accessDeniedHandler(permissionDeniedHandler)//权限不足时候处理方式
            //设置登录地址
            .and()
                .formLogin()
                    .usernameParameter("username")//用户名字段
                    .passwordParameter("password")//密码字段
                    .loginPage(securityProperties.getLoginFormUrl())//登录页面
                    .loginProcessingUrl(securityProperties.getLoginProcessingUrl())//登录后台处理
            //设置登出地址
            .and()
                .logout()
                    .logoutUrl(securityProperties.getLogoutUrl())//登出地址
                    .logoutSuccessHandler(logoutSuccessHandler)//登出成功处理
            //设置一个拒绝访问的提示链接
            .and()
                .authorizeRequests()
                    .anyRequest().authenticated();

    }

    /**
     * 设置不拦截资源服务器的认证请求
     * 比较适合配置前端相关的静态资源，
     * WebSecurity：是完全绕过SpringSecurity的所有filter
     * HttpSecurity：没有绕过SpringSecurity只是允许通过而已
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        List<String> whiteResources = securityProperties.getWhiteResources();
//        String[] resources = CustomSecurityProperties.list2Array(whiteResources);
//        log.info("========>whiteResources = {}", JsonUtils.toJsonMsg(resources));
//        if(ArrayUtils.isNotNull(resources)){
//            web.ignoring().antMatchers(resources);
//        }
    }
}
