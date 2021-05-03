package com.flx.netty.chat.login.console.controller;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 10:04
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/get")
    public ResultResponse get(){
        return ResultResponse.success();
    }

}
