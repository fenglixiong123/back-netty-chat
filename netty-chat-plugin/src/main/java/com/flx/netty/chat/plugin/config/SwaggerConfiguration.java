package com.flx.netty.chat.plugin.config;

import com.flx.netty.chat.plugin.plugins.swagger.SwaggerService;
import com.flx.netty.chat.plugin.plugins.swagger.controller.SwaggerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Fenglixiong
 * @Create 2018.11.07 17:37
 * @Description 主要初始化了一个开发框架给定了一些默认配置(来自properties包里面)的swagger服务
 * 如果在配置文件中配置flx.swagger.basePackage就会默认以这个包来进行扫描swagger文件
 **/
@Slf4j
@Configuration
@EnableSwagger2
@ConditionalOnJava(value = JavaVersion.EIGHT)
public class SwaggerConfiguration {

    @Bean
    public SwaggerService swaggerService(){
        return new SwaggerService();
    }

    @Bean
    @ConditionalOnMissingBean(SwaggerController.class)
    public SwaggerController swaggerController(){
        return new SwaggerController();
    }

    @Bean
    @ConditionalOnBean(SwaggerService.class)
    public Docket docket(){
        return swaggerService().buildDocket();
    }

}
