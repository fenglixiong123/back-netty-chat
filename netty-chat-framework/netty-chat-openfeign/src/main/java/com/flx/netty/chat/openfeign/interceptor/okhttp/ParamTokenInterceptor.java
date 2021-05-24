package com.flx.netty.chat.openfeign.interceptor.okhttp;

import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.openfeign.utils.OkUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.flx.netty.chat.openfeign.constants.FeignConstant.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 15:54
 * @Description: 适用于从请求参数或者请求头中获取token的情况
 */
@Slf4j
public class ParamTokenInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String token = null;
        if (requestAttributes!=null) {
            HttpServletRequest httpRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            token = httpRequest.getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isNotBlank(token)) {
                log.info("Get token from header successful : token = {}",token);
            }else {
                token = httpRequest.getParameter(ACCESS_TOKEN);
                if (StringUtils.isNotBlank(token)) {
                    token = String.format("%s %s", BEARER_TOKEN_TYPE, token);
                    log.info("Get token from param successful : token = {}", token);
                }
            }
        }
        if(StringUtils.isBlank(token)){
            log.info("Get token error !");
            return OkUtils.buildErrorResponse("Token is required !");
        }else {
            request = request.newBuilder().header(AUTHORIZATION_HEADER, token).build();
            return chain.proceed(request);
        }

    }


}
