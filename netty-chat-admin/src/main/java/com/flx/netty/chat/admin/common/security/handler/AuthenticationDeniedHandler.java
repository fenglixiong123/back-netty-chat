package com.flx.netty.chat.admin.common.security.handler;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/8 19:03
 * @Description: 认证失败的处理方式,Token不正确时候处理方式
 */
@Slf4j
@Component
public class AuthenticationDeniedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("=======>Authentication failure !");
        ResultResponse.printError(response,"401","Sorry,authorize deny !",e.getMessage());
    }

}
