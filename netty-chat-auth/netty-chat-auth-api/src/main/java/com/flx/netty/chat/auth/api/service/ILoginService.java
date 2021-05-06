package com.flx.netty.chat.auth.api.service;

import com.flx.netty.chat.auth.api.vo.LoginVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-auth",path = "/web")
interface ILoginService {

    @PostMapping("/login")
    ResultResponse<String> add(@RequestBody LoginVO entity);

}
