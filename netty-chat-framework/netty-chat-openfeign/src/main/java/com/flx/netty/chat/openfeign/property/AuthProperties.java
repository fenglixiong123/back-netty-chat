package com.flx.netty.chat.openfeign.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 17:49
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "flx.feign.token",ignoreInvalidFields = true)
public class AuthProperties {

    /**
     * 是否自动获取token
     */
    private boolean auto = false;

    /**
     * 认证中心地址
     */
    private String ssoUrl;

}
