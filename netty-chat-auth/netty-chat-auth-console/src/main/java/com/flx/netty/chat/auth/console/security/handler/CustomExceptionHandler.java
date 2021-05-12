package com.flx.netty.chat.auth.console.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * @Author Fenglixiong
 * @Create 2021/5/12 23:54
 * @Description 自定义异常处理
 **/
@Slf4j
@Component
public class CustomExceptionHandler implements WebResponseExceptionTranslator<OAuth2Exception> {

    /**
     * 异常分析器
     */
    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(exception);
        //异常栈获取OAuth2Exception异常
        OAuth2Exception e1 = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if(e1!=null){
            return errorResponse(e1.getHttpErrorCode(),"OAuth2 exception !",e1.getMessage());
        }
        //异常栈获取HttpMethod不支持异常
        HttpRequestMethodNotSupportedException e2 = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if(e2!=null){
            return errorResponse(405,"Method Not Allowed !",e2.getMessage());
        }
        //异常栈获取Redis连接异常
        RedisConnectionFailureException e3 = (RedisConnectionFailureException) throwableAnalyzer.getFirstThrowableOfType(RedisConnectionFailureException.class, causeChain);
        if(e3!=null){
            return errorResponse(600,"Redis connect exception !",e3.getMessage());
        }
        //剩下的是内部异常
        return errorResponse(500,"Internal Server Error !", exception.getMessage());
    }

    /**
     * 返回异常信息
     * @param code 错误代码
     * @param message 错误信息
     * @return
     */
    private ResponseEntity<OAuth2Exception> errorResponse(int code,String message,String data){
        return ResponseEntity
                .status(code)
                .body(new CustomOauthException(message,data));
    }

}
