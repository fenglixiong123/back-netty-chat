package com.flx.netty.chat.plugin.autoconfig;

import com.flx.netty.chat.plugin.config.RedisPubConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 3:08
 * @Description
 **/
@Slf4j
@Import(value = RedisPubConfiguration.class)
public class RedisPubAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               RedisPub Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
