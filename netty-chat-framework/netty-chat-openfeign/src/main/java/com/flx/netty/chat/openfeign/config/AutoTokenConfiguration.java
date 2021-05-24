package com.flx.netty.chat.openfeign.config;

import com.flx.netty.chat.openfeign.interceptor.okhttp.AutoTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 16:51
 * @Description:
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "flx.feign.token",name = "auto",havingValue = "true")
public class AutoTokenConfiguration implements InitializingBean {

    @Bean
    public AutoTokenInterceptor autoTokenInterceptor(){
        return new AutoTokenInterceptor();
    }

    /**
     * 添加feign的默认实现client
     */
    @Bean
    @ConditionalOnBean(AutoTokenInterceptor.class)
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(30, TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//写入超时时间
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(10 , 5L, TimeUnit.MINUTES))
                .addInterceptor(autoTokenInterceptor())
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               AutoToken Success               *");
        log.info("*                                               *");
        log.info("*************************************************");
    }
}
