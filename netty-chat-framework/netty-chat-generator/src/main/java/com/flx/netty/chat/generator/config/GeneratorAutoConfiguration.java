package com.flx.netty.chat.generator.config;

import com.flx.netty.chat.generator.controller.GeneratorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 13:53
 * @Description:
 */
@Slf4j
@Configuration
public class GeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean //不存在的时候在初始化
    public GeneratorController generatorController(){
        return new GeneratorController();
    }

}
