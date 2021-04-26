package com.flx.netty.chat.generator;

import com.flx.netty.chat.plugin.annotion.enable.EnableCross;
import com.flx.netty.chat.plugin.annotion.enable.EnableSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 11:28
 * @Description:
 */
@Slf4j
@EnableCross
@EnableSwagger
@SpringBootApplication(scanBasePackages = "com.flx.netty.chat.generator")
public class AutoCodeApplication {

    public static void main(String[] args) {

        SpringApplication.run(AutoCodeApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               AutoCode Success                *");
        log.info("*                                               *");
        log.info("*************************************************");

    }

}
