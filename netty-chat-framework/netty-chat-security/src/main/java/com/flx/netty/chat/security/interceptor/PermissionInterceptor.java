package com.flx.netty.chat.security.interceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 14:31
 * @Description:
 */
@Slf4j
public class PermissionInterceptor implements Filter{


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    }
}
