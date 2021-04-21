package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.plugins.flyway.FlywayService;
import com.flx.netty.chat.common.property.FlywayMysqlProperties;
import com.flx.netty.chat.common.property.FlywayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:57
 * @Description flyway进行数据库版本的管理
 **/
@Slf4j
@Configuration
//@EnableConfigurationProperties({FlywayMysqlProperties.class,FlywayProperties.class})
public class FlywayConfiguration {

    @Bean
    public FlywayService flywayService(){
        return new FlywayService();
    }

}
