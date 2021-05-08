package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.api.vo.ValidatePassVO;
import com.flx.netty.chat.auth.api.vo.WebUserVO;
import com.flx.netty.chat.auth.console.service.LoginService;
import com.flx.netty.chat.auth.console.service.UserService;
import com.flx.netty.chat.common.utils.result.ResultResponse;
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
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    /**
     * 登录系统
     * @param loginVO
     * @throws Exception
     */
    @Override
    public WebUserVO login(LoginVO loginVO) throws Exception {
//        ResultResponse<WebUserVO> response = userService.validateUser(loginVO.getUsername(), loginVO.getPassword());
        return null;
    }

    /**
     * 注册系统
     * @param webUserVO
     * @return
     * @throws Exception
     */
    @Override
    public Long register(WebUserVO webUserVO) throws Exception {
        return 1L;
    }

    /**
     * 发送注册成功的邮件或者短信
     * @param webUserVO
     */
    private void sendSuccessEmail(WebUserVO webUserVO) {
        //todo
    }
}
