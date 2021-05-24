package com.flx.netty.chat.openfeign.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 18:24
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "flx.feign.okhttp")
public class OkHttpProperties {

    /**
     * 连接超时时间
     */
    private long connectTimeout = 60;

    /**
     * 读取超时时间
     */
    private long readTimeout = 300;

    /**
     * 写入超时时间
     */
    private long writeTimeout = 300;

}
