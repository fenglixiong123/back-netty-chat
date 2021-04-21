package com.flx.netty.chat.plugin.autoconfig;


import com.flx.netty.chat.plugin.config.MybatisPlusConfiguration;
import com.flx.netty.chat.plugin.plugins.mybatis.common.TableFieldAlias;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @Author Fenglixiong
 * @Create 2020/8/4 0:55
 * @Description
 **/
@Slf4j
@Import(value = {MybatisPlusConfiguration.class, TableFieldAlias.class})
public class MyBatisAutoConfiguration {

    @PostConstruct
    public void init(){
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*                Mybatis Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
