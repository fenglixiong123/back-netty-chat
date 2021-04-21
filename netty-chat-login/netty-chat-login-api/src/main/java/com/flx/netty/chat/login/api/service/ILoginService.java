package com.flx.netty.chat.login.api.service;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.login.api.vo.LoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-login",path = "/web")
interface ILoginService {

    @PostMapping("/login")
    ResultResponse<String> add(@RequestBody LoginVO entity);

}
