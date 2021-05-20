package com.flx.netty.chat.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 19:32
 * @Description:
 */
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.flx.netty.chat.auth.api"})
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, FlywayAutoConfiguration.class})
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
