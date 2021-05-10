package com.flx.netty.chat.security.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author Fenglixiong
 * @Create 2021/5/11 2:09
 * @Description 定义Feign的拦截器
 **/
@Slf4j
@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    //请求头中的token
    private final String AUTHORIZATION_HEADER = "Authorization";

    /**
     *
     * @param requestTemplate feign底层用来发封装请求的对象
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //1.获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //2.获取请求头中的Token
        String token = requestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER);
        log.info("Feign拦截器添加请求头：{}",token);
        //3.添加到Feign的请求头
        requestTemplate.header(AUTHORIZATION_HEADER,token);
    }

}
