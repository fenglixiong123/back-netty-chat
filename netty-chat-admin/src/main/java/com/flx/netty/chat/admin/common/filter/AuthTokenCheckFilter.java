package com.flx.netty.chat.admin.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.admin.common.wrapper.RequestWrapper;
import com.flx.netty.chat.admin.common.wrapper.ResponseWrapper;
import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.http.OkUtils;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.flx.netty.chat.openfeign.interceptor.FeignTokenRequestInterceptor.*;

/**
 * @Author Fenglixiong
 * @Create 2021/5/21 1:48
 * @Description 拦截/auth/**的请求，获取token然后进行业务访问
 **/
@Slf4j
@WebFilter(urlPatterns = "/auth/*",filterName = "auth-token-filter")
public class AuthTokenCheckFilter implements Filter {

    /**
     * 请求token
     */
    public static AtomicReference<String> AUTH_TOKEN = new AtomicReference<>();

    private static final String SSO_URL = "http://"+ PropertyUtils.get("sso.ip","127.0.0.1") +":8001/oauth/token";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>Load AuthTokenFilter<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        //如果token为空则需要获得
        if(StringUtils.isBlank(AUTH_TOKEN.get())){
            log.info("Request url = {} token is null,ready to load token from sso !",request.getRequestURL());
            boolean result = loadAuthToken(response, SSO_URL);
            if(!result){
                return;
            }
        }

        //读取request
        RequestWrapper requestWrapper = new RequestWrapper(request);
        //读取response
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        //继续执行filter
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        }finally {
            //执行完成，得到响应
            String requestBody = requestWrapper.getBody();
            String responseBody = responseWrapper.getBody();
            responseWrapper.reSendResponse(responseBody);
        }
    }

    /**
     * 获取token
     */
    private synchronized boolean loadAuthToken(HttpServletResponse response, String url) {
        if(StringUtils.isNotBlank(AUTH_TOKEN.get())){
            return true;
        }

        Map<String,String> query = new HashMap<>();
        query.put("username","master");
        query.put("password","master123");
        query.put("grant_type","password");
        query.put("client_id","netty-chat-admin");
        query.put("client_secret","123456");
        try {
            JSONObject result = OkUtils.postJSON(url, query, null);
            Object accessToken = result.get(ACCESS_TOKEN);
            if(Objects.isNull(accessToken)){
                log.error("Get access token from authServer error : {}",result.toString());
                throw new RuntimeException("Access token is null !");
            }
            AUTH_TOKEN.set(accessToken.toString());
            log.info("Get authToken successful,token = {}",accessToken);
            return true;
        } catch (Exception e) {
            log.error("LoadAuthToken error : {}",e.getMessage());
            ResultResponse.printError(response,"503","LoadAuthToken post error !",e.getMessage());
            return false;
        }
    }

    @Override
    public void destroy() {

    }

}
