package com.flx.netty.chat.admin.common.security.handler;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/8 19:07
 * @Description: 权限不足异常处理
 */
@Slf4j
@Component
public class PermissionDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("=======>Permission deny !");
        ResultResponse.printError(response,"403","Sorry,permission deny !",e.getMessage());
    }

}
