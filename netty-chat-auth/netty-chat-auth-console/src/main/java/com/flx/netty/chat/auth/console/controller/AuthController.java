package com.flx.netty.chat.auth.console.controller;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/9 21:54
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

//    @GetMapping("/logout")
//    public String getUser(@RequestParam String access_token){
//        log.info("logout token = {}",access_token);
//        return "logout success";
//    }

    /**
     * 资源服务器获取用户信息的接口
     * RemoteTokenServices对Token进行验证
     * 如果其他资源服务需要验证Token,则需要远程调用授权服务暴露的验证Token的API接口。
     * @param
     * @return
     */
    @GetMapping("/checkToken")
    public Principal getUser(Principal principal){
        log.info("checkToken : {}", JsonUtils.toJsonMsg(principal));
        return principal;
    }

    @GetMapping("/student")
    public String getStudent(){
        return "student";
    }

    @GetMapping("/user/user1")
    public String getUser1(){
        return "user1";
    }

    @GetMapping("/user/user2")
    public String getUser2(){
        return "user2";
    }

    @GetMapping("/person/person1")
    public String getUser3(){
        return "person1";
    }

    @GetMapping("/person/person2")
    public String getUser4(){
        return "person2";
    }

}
