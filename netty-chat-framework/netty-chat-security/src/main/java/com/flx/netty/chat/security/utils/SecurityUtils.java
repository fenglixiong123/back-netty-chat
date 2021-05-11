package com.flx.netty.chat.security.utils;

import com.flx.netty.chat.security.entity.CustomAuthority;
import com.flx.netty.chat.security.entity.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 *
 */
public class SecurityUtils {

    /**
     * 用于获得当前用户信息
     */
    public static CustomUserDetails currentUser() {
        return (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
