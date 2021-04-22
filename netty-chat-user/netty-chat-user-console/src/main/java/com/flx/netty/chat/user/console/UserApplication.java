package com.flx.netty.chat.user.console;

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
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.user"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                UserApp Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
