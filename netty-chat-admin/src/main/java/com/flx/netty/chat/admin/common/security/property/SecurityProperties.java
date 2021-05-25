package com.flx.netty.chat.admin.common.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "flx.security")
public class SecurityProperties {

    /**
     * 免授权URL
     */
    private List<String> whitePermits;

    /**
     * 免授权资源
     */
    private List<String> whiteResources;

    /**
     * 登录处理地址
     */
    private String loginProcessingUrl;

    /**
     * 登出路径
     */
    private String logoutUrl;

}
