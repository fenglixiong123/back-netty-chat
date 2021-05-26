package com.flx.netty.chat.admin.common.security.handler;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/25 18:04
 * @Description: 登录成功之后返回用户token信息
 */
@Slf4j
@Component
public class LoginSuccessfulHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("++++++++++++++++++++++login successful !");
        ResultResponse.printSuccess(response,"恭喜，登录成功！",authentication);
    }

}
