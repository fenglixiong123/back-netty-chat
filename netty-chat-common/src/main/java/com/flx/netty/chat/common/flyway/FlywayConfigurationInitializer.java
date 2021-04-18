package com.flx.netty.chat.common.flyway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Fenglixiong
 * @Date: 2020/8/4 18:47
 *
 * 其他项目使用flyway sdk
 *
 * 需要保证flyway最早执行将数据库刷新为最新状态
 * 这里采用ApplicationContextInitializer方式加载flyway进行sql迁移，是防止数据库不是最新的导致执行某些sql失败
 * classpath:/resource/META-INF/spring.factories 添加下面类，springboot启动会首先加载这个
 * org.springframework.context.ApplicationContextInitializer=com.flx.springboot.scaffold.flyway.config.FlywayConfigurationInitializer
 */
@Slf4j
public class FlywayConfigurationInitializer implements ApplicationContextInitializer {

    @Autowired
    private FlywayService flywayService;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        flywayService.initFlyway();

    }
}
