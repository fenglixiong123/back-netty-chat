package com.flx.netty.chat.login.console.service.impl;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.login.api.vo.LoginVO;
import com.flx.netty.chat.login.console.service.LoginService;
import com.flx.netty.chat.user.api.service.IUserService;
import com.flx.netty.chat.user.api.vo.ValidatePassVO;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private IUserService userService;

    /**
     * 登录系统
     * @param loginVO
     * @throws Exception
     */
    @Override
    public WebUserVO login(LoginVO loginVO) throws Exception {
        ResultResponse<WebUserVO> response = userService.validateUser(new ValidatePassVO(loginVO.getUsername(), loginVO.getPassword()));
        if(response.isSuccess()){
            return response.getData();
        }else {
            throw new Exception("登录失败！");
        }
    }

    /**
     * 注册系统
     * @param webUserVO
     * @return
     * @throws Exception
     */
    @Override
    public Long register(WebUserVO webUserVO) throws Exception {
        ResultResponse<Long> response = userService.add(webUserVO);
        if(response.isSuccess()){
            sendSuccessEmail(webUserVO);
            return response.getData();
        }
        throw new Exception("注册失败！");
    }

    /**
     * 发送注册成功的邮件或者短信
     * @param webUserVO
     */
    private void sendSuccessEmail(WebUserVO webUserVO) {
        //todo
    }
}
