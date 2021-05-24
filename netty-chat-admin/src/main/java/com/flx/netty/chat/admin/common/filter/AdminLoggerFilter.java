package com.flx.netty.chat.admin.common.filter;

import com.flx.netty.chat.admin.common.wrapper.RequestWrapper;
import com.flx.netty.chat.admin.common.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Fenglixiong
 * @Create 2021/5/21 1:48
 * @Description 拦截/auth/**的请求，获取token然后进行业务访问
 **/
@Slf4j
@WebFilter(urlPatterns = {"/admin/*","/auth/*"},filterName = "admin-logger-filter")
public class AdminLoggerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>Load AuthTokenFilter<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);

        RequestWrapper requestWrapper = new RequestWrapper(request);
        //读取response
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        //继续执行filter
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        }finally {
            String requestBody = requestWrapper.getBody();
            log.info("Admin requestBody = {}",requestBody);
            //执行完成，得到响应
            String responseBody = responseWrapper.getBody();
            log.info("Admin responseBody = {}",responseBody);
            responseWrapper.reSendResponse(responseBody);
        }
    }


    @Override
    public void destroy() {

    }

}
