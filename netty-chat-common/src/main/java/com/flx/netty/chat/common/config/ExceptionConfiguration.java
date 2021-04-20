package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.exception.handler.CoreExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/28 15:53
 * @Description: 异常配置类用来注入exception类
 */
@Slf4j
@Configuration
public class ExceptionConfiguration {

    @Bean
    public CoreExceptionHandler coreExceptionHandler(){
        return new CoreExceptionHandler();
    }

}
