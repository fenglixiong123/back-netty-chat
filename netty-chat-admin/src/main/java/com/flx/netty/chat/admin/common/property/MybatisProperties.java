package com.flx.netty.chat.admin.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/11 10:57
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "flx.mybatis",ignoreInvalidFields = true)
public class MybatisProperties {

    private boolean logOpen = false;

}
