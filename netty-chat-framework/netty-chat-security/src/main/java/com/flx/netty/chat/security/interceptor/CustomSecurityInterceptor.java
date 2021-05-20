package com.flx.netty.chat.security.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 14:31
 * @Description:
 */
@Slf4j
public class CustomSecurityInterceptor implements Filter{


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



    }




}
