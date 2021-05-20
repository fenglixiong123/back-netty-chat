package com.flx.netty.chat.admin.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.http.OkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.flx.netty.chat.openfeign.interceptor.FeignTokenRequestInterceptor.*;

/**
 * @Author Fenglixiong
 * @Create 2021/5/21 1:48
 * @Description 拦截/auth/**的请求，获取token然后进行业务访问
 **/
@Slf4j
@Order(2)
@WebFilter(urlPatterns = "/auth/**",filterName = "auth-token-filter")
public class AuthTokenFilter implements Filter {

    /**
     * 请求token
     */
    private static String authToken = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>Load AuthTokenFilter<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        //如果token为空则需要获得
        if(StringUtils.isBlank(authToken)){
            log.info("Request url = {} authToken is null !",request.getRequestURL());
            loadAuthToken();
        }
        //修改header
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request){
            @Override
            public String getHeader(String name) {
                String superHeader = super.getHeader(name);
                if(AUTHORIZATION_HEADER.equals(name) && StringUtils.isBlank(superHeader)){
                    return String.format("%s %s", BEARER_TOKEN_TYPE, authToken);
                }
                return super.getHeader(name);
            }
        };
        filterChain.doFilter(requestWrapper,servletResponse);
    }

    /**
     * 获取token
     */
    private synchronized void loadAuthToken() {
        if(StringUtils.isNotBlank(authToken)){
            return;
        }
        String url = "http://127.0.0.1:8001/oauth/token";
        Map<String,String> query = new HashMap<>();
        try {
            JSONObject result = OkUtils.postJSON(url, query, null);
            String accessToken = result.get(ACCESS_TOKEN).toString();
            if(StringUtils.isNotBlank(accessToken)){
                authToken = accessToken;
                log.info("Get authToken successful !");
            }
        } catch (Exception e) {
            log.error("loadAuthToken error : {}",e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }

}
