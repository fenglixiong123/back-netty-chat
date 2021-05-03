package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.plugins.kaptcha.KaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:57
 * @Description 登录验证码
 **/
@Slf4j
@Configuration
public class KaptchaConfiguration {

    @Bean
    @ConditionalOnBean(RedisConfiguration.class)
    public KaptchaService kaptchaService(){
        return new KaptchaService();
    }

}
