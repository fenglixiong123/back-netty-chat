package com.flx.netty.chat.auth.console.service;


import com.flx.netty.chat.auth.api.vo.LoginVO;
import com.flx.netty.chat.auth.api.vo.AuthUserVO;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/13 15:45
 * @Description:
 */
public interface AuthLoginService {

    /**
     * 登录
     * @param loginVO
     * @return
     * @throws Exception
     */
    AuthUserVO login(LoginVO loginVO) throws Exception;

    /**
     * 注册
     * @param entityVO
     * @return
     * @throws Exception
     */
    Long register(AuthUserVO entityVO) throws Exception;

}
