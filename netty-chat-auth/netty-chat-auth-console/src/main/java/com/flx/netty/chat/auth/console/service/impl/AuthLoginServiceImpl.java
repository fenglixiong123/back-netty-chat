package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.AuthUserVO;
import com.flx.netty.chat.auth.console.service.AuthLoginService;
import com.flx.netty.chat.auth.console.service.AuthUserService;
import com.flx.netty.chat.auth.api.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/13 15:46
 * @Description:
 */
@Slf4j
@Service
public class AuthLoginServiceImpl implements AuthLoginService {

    @Autowired
    private AuthUserService userService;

    /**
     * 登录系统
     * @param loginVO
     * @throws Exception
     */
    @Override
    public AuthUserVO login(LoginVO loginVO) throws Exception {
//        ResultResponse<WebUserVO> response = userService.validateUser(loginVO.getUsername(), loginVO.getPassword());
        return null;
    }

    /**
     * 注册系统
     * @param authUserVO
     * @return
     * @throws Exception
     */
    @Override
    public Long register(AuthUserVO authUserVO) throws Exception {
        return 1L;
    }

    /**
     * 发送注册成功的邮件或者短信
     * @param authUserVO
     */
    private void sendSuccessEmail(AuthUserVO authUserVO) {
        //todo
    }
}
