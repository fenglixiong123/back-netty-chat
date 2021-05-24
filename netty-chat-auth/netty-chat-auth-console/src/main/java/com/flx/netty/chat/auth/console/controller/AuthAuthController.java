package com.flx.netty.chat.auth.console.controller;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/9 21:54
 * @Description:
 */
@Slf4j
@RestController
public class AuthAuthController {

    /**
     * 资源服务器获取用户信息的接口
     * RemoteTokenServices对Token进行验证
     * 如果其他资源服务需要验证Token,则需要远程调用授权服务暴露的验证Token的API接口。
     * @param
     * @return
     */
    @GetMapping("/auth/checkToken")
    public Principal getUser(Principal principal){
        log.info("checkToken : {}", JsonUtils.toJsonMsg(principal));
        return principal;
    }

    @GetMapping("/jack")
    public String jack(){
        return "jack";
    }

    @GetMapping("/marry")
    public String marry(){
        return "marry";
    }

    @GetMapping("/auth/user/user1")
    public String getUser1(){
        return "user1";
    }

    @GetMapping("/auth/user/user2")
    public String getUser2(){
        return "user2";
    }

    @GetMapping("/auth/person/person1")
    public String getUser3(){
        return "person1";
    }

    @GetMapping("/auth/person/person2")
    public String getUser4(){
        return "person2";
    }

}
