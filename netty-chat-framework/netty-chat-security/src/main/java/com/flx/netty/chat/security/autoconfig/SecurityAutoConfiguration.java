package com.flx.netty.chat.security.autoconfig;

import com.flx.netty.chat.security.config.AppBeanConfiguration;
import com.flx.netty.chat.security.config.OAuth2ResourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 15:53
 * @Description:
 */
@Slf4j
@Import(value = {
        AppBeanConfiguration.class,
        OAuth2ResourceConfig.class})
public class SecurityAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*           SecurityResource Success            *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
