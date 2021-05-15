package com.flx.netty.chat.gateway.controller;

import com.flx.netty.chat.gateway.utils.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Fenglixiong
 * @Create 2021/5/15 3:03
 * @Description
 **/
@Slf4j
@RestController
public class FallbackController {

    /**
     * 当后台服务不可用的时候触发熔断，调用此默认处理方式
     * @return
     */
    @RequestMapping("/defaultFallback")
    public SimpleResponse<String> defaultFallback() {
        log.error("Hystrix is using ...");
        return SimpleResponse.error("服务暂时不可用，请联系管理员处理！");
    }

}
