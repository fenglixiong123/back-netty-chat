package com.flx.netty.chat.admin.common.security.filter;

import com.alibaba.fastjson.JSON;
import com.flx.netty.chat.admin.common.security.auth.AuthManager;
import com.flx.netty.chat.admin.common.security.handler.LoginFailureHandler;
import com.flx.netty.chat.admin.common.security.handler.LoginSuccessfulHandler;
import com.flx.netty.chat.admin.common.security.property.SecurityProperties;
import com.flx.netty.chat.admin.common.security.user.SystemUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/26 10:38
 * @Description: 登录处理器,当请求/admin/login/process
 */
@Slf4j
@Component
public class AuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthManager authManager;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginSuccessfulHandler loginSuccessfulHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    public AuthenticationProcessFilter(SecurityProperties securityProperties) {
        super(new AntPathRequestMatcher(securityProperties.getLoginProcessingUrl(),"POST"));
    }

    /**
     * 处理登录请求
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getParameter(securityProperties.getUsernameParam()),
                request.getParameter(securityProperties.getPasswordParam()));
        Authentication authenticate = getAuthenticationManager().authenticate(authenticationToken);
        //如果校验成功
        if(authenticate.isAuthenticated()){
            try {
                SystemUserDetails userDetails = (SystemUserDetails) authenticate.getPrincipal();
                String token = authManager.getToken(userDetails);
                userDetails.setToken(token);
            } catch (Exception e) {
                authenticate.setAuthenticated(false);
                log.error("Auth error : {}",e.getMessage());
            }
        }
        return authenticate;
    }

    @PostConstruct
    public void init(){
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationSuccessHandler(loginSuccessfulHandler);
        super.setAuthenticationFailureHandler(loginFailureHandler);
    }

}
