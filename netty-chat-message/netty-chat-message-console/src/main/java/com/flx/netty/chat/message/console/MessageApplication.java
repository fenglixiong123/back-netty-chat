package com.flx.netty.chat.message.console;

import com.flx.netty.chat.plugin.annotion.enable.EnableBase;
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
@SpringBootApplication(scanBasePackages = {"com.flx.netty.chat.message"})
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*              MessageApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
