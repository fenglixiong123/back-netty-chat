package com.flx.netty.chat.group.console.controller;

import com.flx.netty.chat.auth.api.client.UserClient;
import com.flx.netty.chat.auth.api.vo.WebUserVO;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 11:22
 * @Description:
 */
@Api(tags = "群管理")
@RestController
@RequestMapping("/web/home")
public class HomeController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/currentUser")
    public ResultResponse getCurrentUser(){
        try {
            return ResultResponse.success(SecurityUtils.currentUser());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            ResultResponse<WebUserVO> response = userClient.get(id);
            System.out.println(JsonUtils.toJsonMsg(response));
            return ResultResponse.success(response.getData());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
