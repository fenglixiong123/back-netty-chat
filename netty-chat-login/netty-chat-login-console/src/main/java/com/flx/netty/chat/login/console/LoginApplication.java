package com.flx.netty.chat.login.console;

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
@EnableFeignClients(basePackages = {"com.flx.netty.chat.user.api"})
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.login"})
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                LoginApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
