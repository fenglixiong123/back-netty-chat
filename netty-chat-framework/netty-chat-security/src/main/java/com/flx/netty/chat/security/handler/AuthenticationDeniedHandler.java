package com.flx.netty.chat.security.handler;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/5 2:39
 * @Description: 自定义授权认证异常处理
 */
public class AuthenticationDeniedHandler extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResultResponse.printError(response,"401","Sorry,resource authorize deny !",e.getMessage());
    }
}
