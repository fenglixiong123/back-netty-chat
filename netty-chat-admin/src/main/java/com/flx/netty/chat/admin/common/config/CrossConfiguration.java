package com.flx.netty.chat.admin.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/16 15:10
 * @Description: 解决跨域请求问题
 */
@Slf4j
@Configuration
public class CrossConfiguration implements WebMvcConfigurer {

    /**
     * 就是注册的过程，注册Cors协议的内容。
     * 如： Cors协议支持哪些请求URL，支持哪些请求类型，请求时处理的超时时长是什么等。
     * @param registry 注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 所有的当前站点的请求地址，都支持跨域访问
                .allowedOrigins("*")// 所有的外部域都可跨域访问。
                .allowCredentials(true)// 是否支持跨域用户凭证
                .allowedMethods("GET", "POST", "DELETE", "PUT")// 当前站点支持的跨域请求类型是什么
                .maxAge(3600);// 超时时长设置为1小时。 时间单位是秒
    }

}
