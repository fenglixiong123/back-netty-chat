package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.flyway.FlywayService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:57
 * @Description
 **/
@Configuration
public class FlywayConfiguration {

    @Bean
    public FlywayService flywayService(){
        return new FlywayService();
    }

}
