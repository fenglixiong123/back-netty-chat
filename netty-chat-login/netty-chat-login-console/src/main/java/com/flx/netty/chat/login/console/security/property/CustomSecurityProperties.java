package com.flx.netty.chat.login.console.security.property;

import com.flx.netty.chat.common.utils.ArrayUtils;
import com.flx.netty.chat.common.utils.CollectionUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 10:50
 * @Description:
 */
@Data
@Component
@PropertySource(value = "classpath:/security.properties")
@ConfigurationProperties(prefix = "flx.security")
public class CustomSecurityProperties {

    /**
     * 登录地址
     */
    private String loginFormUrl = "/web/login";

    /**
     * 登出路径
     */
    private String logoutUrl = "/web/logout";

    /**
     * 白名单路径url
     */
    private List<String> whitePermits;

    /**
     * 白名单资源resource
     */
    private List<String> whiteResources;

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

    /**
     * list转换成不重复的数组
     * @param source
     * @return
     */
    public static String[] list2Array(List<String> source){
        Set<String> whiteSet = new HashSet<>();
        if(CollectionUtils.isNotEmpty(source)){
            for (String item : source){
                whiteSet.addAll(Arrays.asList(item.split(",")));
            }
        }
        return whiteSet.toArray(new String[source.size()]);
    }

}
