package com.flx.netty.chat.auth.console.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
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
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        //异常栈获取InvalidTokenException异常
        InvalidTokenException tokenException = (InvalidTokenException) throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
        if(tokenException!=null){
            return errorResponse(tokenException.getHttpErrorCode(),"Token invalid exception !",tokenException.getMessage());
        }
        //异常栈获取OAuth2Exception异常
        OAuth2Exception oAuth2Exception = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if(oAuth2Exception!=null){
            return errorResponse(oAuth2Exception.getHttpErrorCode(),oAuth2Exception.getMessage(),"OAuth2 exception !");
        }
        //异常栈获取Redis连接异常
        RedisConnectionFailureException redisException = (RedisConnectionFailureException) throwableAnalyzer.getFirstThrowableOfType(RedisConnectionFailureException.class, causeChain);
        if(redisException!=null){
            return errorResponse(600,"Redis connect exception !",redisException.getMessage());
        }
        //剩下的是内部异常
        return errorResponse(500,"Internal Server Error !", e.getMessage());
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
