package com.flx.netty.chat.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.annotation.PostConstruct;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 17:21
 * @Description: 自动扫描注册properties文件
 *
 * 正常使用配置文件的方式有：
 *
 * 1.@EnableConfigurationProperties({SwaggerProperties.class})//使得@ConfigurationProperties 注解生效
 * >>>让配置文件生效方式：
 *
 * 1.直接注解方式(需要被IOC容器管理)
 *      a.@Componet //可以被Spring IOC管理
 *      b.@ConfigurationProperties({SwaggerInfoProperties.class}) //配置文件注解
 *
 * 2.配置类方式(需要被IOC容器管理)
 *      a.properties类加注解@ConfigurationProperties(prefix = "flx.swagger")
 *      b.项目配置类上加@EnableConfigurationProperties({SwaggerInfoProperties.class})
 *
 * 3.直接扫描包的形式
 *      @ConfigurationPropertiesScan(basePackages = {"com.flx.netty.chat.common.property"})
 *      basePackages为自定义properties文件的位置
 *
 * >>>使用方式：
 *
 * @Service
 * class HelloService{
 *      @Autowired
 *      private SwaggerInfoProperties config;
 * }
 */
@Slf4j
@ConfigurationPropertiesScan(basePackages = {"com.flx.**.property"})
public class PropertyAutoConfiguration {

    @PostConstruct
    public void init(){
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               Property Success                *");
        log.info("*                                               *");
        log.info("*************************************************");
    }

}
