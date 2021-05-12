package com.flx.netty.chat.security.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "flx.auth2.resource")
public class SecurityResourceProperties {

    /**
     * Auth服务的资源ID
     */
    private String resourceId;

    /**
     * 所有资源可以访问
     */
    private boolean permitAll = false;

    /**
     * 需要拦截的url
     */
    private List<String> authUrls;

    /**
     * 开放的URL
     */
    private List<String> passUrls;

    /**
     * Token存储的数据库
     */
    private int redisIndex = 1;

    /**
     * token的存储方式
     */
    private String tokenStore;


}
