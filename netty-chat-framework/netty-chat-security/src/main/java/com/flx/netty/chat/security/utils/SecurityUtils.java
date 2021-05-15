package com.flx.netty.chat.security.utils;

import com.flx.netty.chat.security.entity.CustomAuthority;
import com.flx.netty.chat.security.entity.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Set;

/**
 * 参考文章：https://www.cnblogs.com/longfurcat/p/9417912.html
 *
 * SecurityContextHolder(默认ThreadLocal模式存储上下文信息)
 *      |--->getContext()   获取上下文对象
 *      |--->setContext()   设置上下文对象
 *      |--->clearContext() 清理上下文对象
 * SecurityContext
 *      |--->getAuthentication()    获取用户认证信息
 *      |--->setAuthentication()    设置用户认证信息
 * Authentication
 *      |--->getAuthorities()       获取用户权限
 *      |--->getCredentials()
 *      |--->getDetails()           用户信息UserDetails
 *      |--->getPrincipal()
 *      |--->isAuthenticated()
 *
 * 由于我们采用微服务本身验证token的方式：
 *
 *      |--->当请求到来的时候
 *      |--->security获得token
 *      |--->TokenService决定验证token方式(本地)
 *      |--->TokenService读取redis中用户认证信息
 *      |--->存入SecurityContextHolder中
 */
public class SecurityUtils {

    /**
     * 用于获得当前用户信息
     */
    public static CustomUserDetails currentUser() {
        return (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取Token
     */
    public static String getToken(){
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details.getTokenValue();
    }

    /**
     *
     * 用于获得当前用户的角色id
     */
    public static Set<String> getRoleCodes() {
        return currentUser().getRoleCodes();
    }

    /**
     *
     * 用于获得当前权限列表
     */
    public static Set<CustomAuthority> getGrantedAuthorities() {
        return currentUser().getAuthorities();
    }

    /**
     *
     * 是否含有该权限
     */
    public static boolean isGrantedAuthority(String authCode) {
        return currentUser().getAuthorities().contains(authCode);
    }

    /**
     * 用于获得当前用户属性
     */
    public static Object getParam(String key) {
        return currentUser().getInfoMap().get(key);
    }
}
