package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.exception.handler.CoreExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/28 15:53
 * @Description: 异常配置类用来注入exception类
 */
@Configuration
public class ExceptionConfiguration {

    @Bean
    public CoreExceptionHandler coreExceptionHandler(){
        return new CoreExceptionHandler();
    }

}
