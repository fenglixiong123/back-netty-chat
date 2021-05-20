package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/20 12:30
 * @Description:
 */
@Slf4j
@Api(tags = "Admin管理")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ResultResponse get(){
        return ResultResponse.success("Hello");
    }

}
