package com.flx.netty.chat.admin.common.security.handler;

import com.flx.netty.chat.admin.common.security.auth.AuthManager;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/25 17:49
 * @Description:
 */
@Slf4j
@Component
public class LogoutSuccessfulHandler implements LogoutSuccessHandler {

    @Autowired
    private AuthManager authManager;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("=======>Logout successful !");
        try {
            authManager.removeToken(null);
            ResultResponse.printSuccess(response,"恭喜，注销成功！");
        } catch (Exception e) {
            log.error("Logout failure : {}",e.getMessage());
            ResultResponse.printError(response,"Sorry，注销失败！");
        }
    }

}
