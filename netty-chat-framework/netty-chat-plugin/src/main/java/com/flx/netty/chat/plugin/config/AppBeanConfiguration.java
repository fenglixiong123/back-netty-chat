package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.property.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/11 11:12
 * @Description:
 * 正常使用配置文件的方式有：
 *
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
 *      @ ConfigurationPropertiesScan(basePackages = {"com.flx.**.property"})
 *      basePackages为自定义properties文件的位置
 *
 * >>>使用方式：
 *
 * 直接在使用的地方注入
 *
 * 如果发现注入失败(比如在@Configuration中注入会注入失败)，则可以采用变量注入的方式
 * public AppBean appBean(SwaggerInfoProperties properties){
 *     //这样注入更加精准
 * }
 *
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        SwaggerProperties.class,
        MybatisPlusProperties.class,
        FlywayProperties.class,
        FlywayMysqlProperties.class,
        RedisMessageProperties.class})
public class AppBeanConfiguration {



}
