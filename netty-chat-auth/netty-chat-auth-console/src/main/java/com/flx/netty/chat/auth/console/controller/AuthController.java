package com.flx.netty.chat.auth.console.controller;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/9 21:54
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/auth/user")
public class AuthController {

    /**
     * 资源服务器获取用户信息的接口
     * RemoteTokenServices对Token进行验证
     * 如果其他资源服务需要验证Token,则需要远程调用授权服务暴露的验证Token的API接口。
     * @param principal
     * @return
     */
//    @GetMapping("/current")
//    public Principal getUser(Principal principal){
//        log.info("getUser : {}", JsonUtils.toJsonMsg(principal));
//        return principal;
//    }

}
