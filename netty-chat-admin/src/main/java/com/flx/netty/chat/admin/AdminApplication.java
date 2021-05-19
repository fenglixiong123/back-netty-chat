package com.flx.netty.chat.admin;

import com.flx.netty.chat.plugin.annotion.enable.EnableBase;
import com.flx.netty.chat.plugin.annotion.enable.EnableRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 19:32
 * @Description:
 */
@Slf4j
@EnableBase
@EnableRedis
@SpringBootApplication
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
