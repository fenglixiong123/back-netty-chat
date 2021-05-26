package com.flx.netty.chat.admin.common.security.token;

import com.flx.netty.chat.admin.common.redis.RedisBaseService;
import com.flx.netty.chat.admin.common.redis.RedisStringService;
import com.flx.netty.chat.admin.common.security.property.TokenProperties;
import com.flx.netty.chat.admin.common.security.user.SystemUserDetails;
import com.flx.netty.chat.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/26 13:39
 * @Description:
 */
@Slf4j
@Component
public class TokenManager {

    private static final String TOKEN = "token:";
    private static final String USER = "user_to_access:";

    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private RedisStringService stringService;
    @Autowired
    private RedisBaseService baseService;

    /**
     * 创建token
     * @param user
     * @return
     */
    public String createToken(SystemUserDetails user) throws Exception{
        if(user==null){
            throw new Exception("Bad param user is null!");
        }
        Object existToken = stringService.get(USER + user.getUsername());
        String token;
        if(existToken!=null){
            token = existToken.toString();
        }else {
            token = UUID.randomUUID().toString().replace("-", "");
        }
        stringService.set(USER+user.getUsername(),token,tokenProperties.getExpireTime(), TimeUnit.SECONDS);
        stringService.set(TOKEN+token,user,tokenProperties.getExpireTime(), TimeUnit.SECONDS);
        return token;
    }

    /**
     * 刷新用户
     * @param token
     */
    public void refreshToken(String token)throws Exception{
        if(StringUtils.isBlank(token)){
            throw new Exception("Bad param token is null!");
        }
        SystemUserDetails user = (SystemUserDetails) stringService.get(TOKEN+token);
        if(baseService.hasKey(TOKEN+token)){
            baseService.expire(TOKEN+token,tokenProperties.getExpireTime(),TimeUnit.SECONDS);
            baseService.expire(USER+user.getUsername(),tokenProperties.getExpireTime(),TimeUnit.SECONDS);
        }
    }

    /**
     * 用户退出登陆
     * @param token
     */
    public void removeToken(String token)throws Exception{
        if(StringUtils.isBlank(token)){
            throw new Exception("Bad param token is null!");
        }
        SystemUserDetails user = (SystemUserDetails) stringService.get(TOKEN+token);
        baseService.delete(TOKEN+token);
        baseService.delete(USER+user.getUsername());
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public SystemUserDetails getUserInfo(String token) throws Exception{
        if(StringUtils.isBlank(token)){
            throw new Exception("Bad param token is null!");
        }
        Object result = stringService.get(TOKEN + token);
        return (SystemUserDetails)result;
    }

}
