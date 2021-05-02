package com.flx.netty.chat.plugin.autoconfig;

import com.flx.netty.chat.plugin.config.KaptchaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:37
 * @Description:
 */
@Slf4j
@Import(value = KaptchaConfiguration.class)
public class KaptchaAutoConfiguration {

    @PostConstruct
    public void init(){
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                Kaptcha Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
