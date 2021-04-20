package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.CrossConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:49
 * @Description:
 */
@Slf4j
@Import(value = CrossConfiguration.class)
public class CrossAutoConfiguration {

    @PostConstruct
    public void init(){
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                 Cross Success                 *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
