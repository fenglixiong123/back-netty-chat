package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.swagger.property.SwaggerProperties;
import com.flx.netty.chat.common.swagger.SwaggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
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
@EnableConfigurationProperties({SwaggerProperties.class})//使得@ConfigurationProperties 注解生效,并且会自动将SwaggerInfoProperties这个类注入到 IOC 容器中
@ConditionalOnJava(value = JavaVersion.EIGHT)
public class SwaggerConfiguration {

    @Bean
    public SwaggerService swaggerService(){
        return new SwaggerService();
    }

    @Bean
    public Docket createRestApi(){
        return swaggerService().buildDocket();
    }

    @GetMapping(value = "/swagger")
    public ModelAndView home() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @GetMapping(value = "/swagger2")
    public DeferredResult<ModelAndView> ui2() {
        DeferredResult<ModelAndView> result = new DeferredResult<>();
        result.setResult(new ModelAndView("redirect:/doc.html"));
        return result;
    }

}
