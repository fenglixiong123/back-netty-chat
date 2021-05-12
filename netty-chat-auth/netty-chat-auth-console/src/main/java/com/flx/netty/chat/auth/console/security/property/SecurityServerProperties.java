package com.flx.netty.chat.auth.console.security.property;

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
@ConfigurationProperties(prefix = "flx.auth2.server")
public class SecurityServerProperties {

    /**
     * 免授权URL
     */
    private List<String> whitePermits;

    /**
     * 免授权资源
     */
    private List<String> whiteResources;

    /**
     * 登录页面
     */
    private String loginFormUrl = "/login.html";

    /**
     * 登录处理地址
     */
    private String loginProcessingUrl = "/auth/loginProcess";

    /**
     * 登出路径
     */
    private String logoutUrl = "/auth/logout";

    /**
     * token的存储方式
     */
    private String tokenStore;

    /**
     * jwt的加密方式(仅当jwt模式下需要)
     */
    private String jwtWay;

    /**
     * jwt签名秘钥，仅当启用jwt的token方式时候起作用
     */
    private String signingKey;

}
