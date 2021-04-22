package com.flx.netty.chat.master.console;

import com.flx.netty.chat.plugin.annotion.enable.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 19:32
 * @Description:
 */
@Slf4j
@EnableBase
@EnableFeignClients(basePackages = {
        "com.flx.netty.chat.user.api",
        "com.flx.netty.chat.group.api",
        "com.flx.netty.chat.message.api"})
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.master"})
public class MasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                MasterApp Success              *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
