package com.flx.netty.chat.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @Author Fenglixiong
 * @Create 2021/5/15 3:24
 * @Description maxInMemorySize配置，解决请求体过大问题
 **/
@Slf4j
@Configuration
@EnableWebFlux
public class WebFluxConfig  implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
    }

}
