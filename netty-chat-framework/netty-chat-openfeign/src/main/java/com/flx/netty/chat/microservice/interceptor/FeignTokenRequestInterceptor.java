package com.flx.netty.chat.microservice.interceptor;

import com.flx.netty.chat.common.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


/**
 * @Author Fenglixiong
 * @Create 2021/5/11 2:09
 * @Description 定义Feign的拦截器
 **/
@Slf4j
@Configuration
public class FeignTokenRequestInterceptor implements RequestInterceptor {

    //请求头中的token
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER_TOKEN_TYPE = "Bearer";
    public final static String ACCESS_TOKEN = "access_token";

    /**
     * 将token封装进入
     * @param requestTemplate feign底层用来发封装请求的对象
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes!=null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tokenFromParam = request.getParameter(ACCESS_TOKEN);
            if (StringUtils.isNotBlank(tokenFromParam)) {
                log.info(">>>>>>> Get token from param : token = {}",tokenFromParam);
                requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, tokenFromParam));
                return;
            }
            String tokenFromHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isNotBlank(tokenFromHeader)) {
                log.info(">>>>>>> Get token from header : token = {}",tokenFromHeader);
                requestTemplate.header(AUTHORIZATION_HEADER, tokenFromHeader);
                return;
            }
            log.info(">>>>>>> Get token error !");
        }
    }

}
