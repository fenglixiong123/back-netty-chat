package com.flx.netty.chat.admin.common.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/26 14:03
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "flx.token")
public class TokenProperties {

    /**
     * token有效期,单位秒60*60=1小时
     */
    private Long expireTime = 1800L;

}
