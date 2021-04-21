package com.flx.netty.chat.login.console.service.impl;

import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.login.api.vo.LoginVO;
import com.flx.netty.chat.login.console.service.LoginService;
import com.flx.netty.chat.user.api.service.IUserService;
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
    public void login(LoginVO loginVO) throws Exception {
        if(StringUtils.isBlank(loginVO.getUsername())){
            throw new Exception("用户名不能为空！");
        }
        if(StringUtils.isBlank(loginVO.getPassword())){
            throw new Exception("密码不能为空！");
        }
        WebUserVO user = (WebUserVO) userService.get("userName", loginVO.getUsername());
        if(user==null){
            throw new Exception("用户名不存在！");
        }
        if(!user.getPassword().equals(loginVO.getPassword())){
            throw new Exception("用户密码错误！");
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
        if(userService.isExist("userName",webUserVO.getUserName())){
            throw new Exception("用户已经存在！");
        }
        Long id = userService.add(webUserVO);
        sendSuccessEmail(webUserVO);
        return id;
    }

    /**
     * 发送注册成功的邮件或者短信
     * @param webUserVO
     */
    private void sendSuccessEmail(WebUserVO webUserVO) {
        //todo
    }
}
