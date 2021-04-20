package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.CacheConfiguration;
import com.flx.netty.chat.common.config.RedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:43
 * @Description
 **/
@Slf4j
@Import({RedisConfiguration.class, CacheConfiguration.class})
public class RedisAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                 Redis Success                 *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}

