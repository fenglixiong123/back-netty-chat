package com.flx.netty.chat.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 14:02
 * @Description:
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class,args);
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*              GatewayApp Success               *");
        log.info("*                                               *");
        log.info("*************************************************");

    }

}
