package com.flx.netty.chat.plugin.autoconfig;

import com.flx.netty.chat.plugin.config.ExceptionConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:34
 * @Description:
 */
@Slf4j
@Import(value = ExceptionConfiguration.class)
public class ExceptionAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               Exception Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
