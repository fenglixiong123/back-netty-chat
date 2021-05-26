package com.flx.netty.chat.admin.common.security.auth;

import com.flx.netty.chat.admin.common.security.token.TokenManager;
import com.flx.netty.chat.admin.common.security.user.SystemUserDetails;
import com.flx.netty.chat.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.flx.netty.chat.admin.common.security.constants.SecurityConstant.HEADER_TOKEN_KEY;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/26 14:36
 * @Description:
 */
@Slf4j
@Component
public class AuthManager {

    @Autowired
    private TokenManager tokenManager;

    /**
     * 获取请求体
     * @return
     */
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public String getToken(String token){
        if(StringUtils.isBlank(token)) {
            return getToken();
        }
        return token;
    }

    public String getToken(){
        HttpServletRequest request = getRequest();
        return request.getHeader(HEADER_TOKEN_KEY);
    }

    /**
     * 登录
     * @param userInfo
     * @return
     */
    public String createToken(SystemUserDetails userInfo)throws Exception{
        return tokenManager.createToken(userInfo);
    }

    /**
     * 获取该访问用户信息
     * @return
     */
    public SystemUserDetails getUserInfo(String token)throws Exception{
        token = getToken(token);
        SystemUserDetails userInfo=tokenManager.getUserInfo(token);
        if(userInfo==null){
            throw new AuthException("Invalid access token !！");
        }
        return userInfo;
    }

    /**
     * 刷新该登录用户，延时
     */
    public void refreshToken(String token)throws Exception{
        token = getToken(token);
        tokenManager.refreshToken(token);
    }

    /**
     * 注销该访问用户
     */
    public void removeToken(String token)throws Exception{
        token = getToken(token);
        tokenManager.removeToken(token);
    }

}
