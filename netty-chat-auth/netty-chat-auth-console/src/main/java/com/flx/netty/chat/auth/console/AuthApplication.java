package com.flx.netty.chat.auth.console;

import com.flx.netty.chat.plugin.annotion.enable.EnableBase;
import com.flx.netty.chat.plugin.annotion.enable.EnableRedis;
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
@EnableRedis
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.auth"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                LoginApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
