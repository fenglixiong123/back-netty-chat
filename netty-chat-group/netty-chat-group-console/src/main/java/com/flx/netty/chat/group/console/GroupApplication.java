package com.flx.netty.chat.group.console;

import com.flx.netty.chat.common.annotion.enable.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 19:32
 * @Description:
 */
@Slf4j
@EnableCross
@EnableFlyway
@EnableMyBatis
@EnableSwagger
@EnableException
//@EnableRedis
@SpringBootApplication
public class GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                GroupApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
