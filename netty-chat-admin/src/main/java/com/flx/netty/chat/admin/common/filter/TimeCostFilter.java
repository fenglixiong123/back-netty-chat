package com.flx.netty.chat.admin.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author Fenglixiong
 * @Create 2021/5/21 2:03
 * @Description
 **/
@Slf4j
@Order(1)
@WebFilter(urlPatterns = {"/admin/**","/auth/**"},filterName = "time-cost-filter")
public class TimeCostFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>Load TimeCostFilter<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        String url = request.getRequestURI();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            long spendTime = System.currentTimeMillis() - startTime;
            log.info("The request of [{}] spend time : {}",url,spendTime);
        }
    }

    @Override
    public void destroy() {

    }
}
