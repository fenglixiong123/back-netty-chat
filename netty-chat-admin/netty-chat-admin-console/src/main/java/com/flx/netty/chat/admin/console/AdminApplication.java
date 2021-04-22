package com.flx.netty.chat.admin.console;

import com.flx.netty.chat.plugin.annotion.enable.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 19:32
 * @Description:
 */
@Slf4j
@EnableBase
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.flx.netty.chat.user.api",
        "com.flx.netty.chat.group.api",
        "com.flx.netty.chat.message.api"})
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.admin"})
public class AdminApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class,args);

        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                AdminApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");

    }

}
