package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.plugins.flyway.FlywayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:57
 * @Description flyway进行数据库版本的管理
 **/
@Slf4j
@Configuration
public class FlywayConfiguration {

    @Bean
    public FlywayService flywayService(){
        return new FlywayService();
    }

}
