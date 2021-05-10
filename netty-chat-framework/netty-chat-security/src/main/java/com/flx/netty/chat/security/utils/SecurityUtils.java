package com.flx.netty.chat.security.utils;

import com.alibaba.fastjson.JSON;
import com.flx.netty.chat.security.entity.CustomAuthority;
import com.flx.netty.chat.security.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class SecurityUtils {


    public static Object getOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    /**
     * 用于获得当前用户信息
     */
    public static CustomUserDetails currentUser() {
        return JSON.parseObject(JSON
                .parseObject(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()))
                .getJSONObject("userAuthentication").getJSONObject("details").getJSONObject("principal").toJSONString(),
                CustomUserDetails.class);
    }

    /**
     *
     * 用于获得当前用户的角色id
     */
    public static Set<String> getRoleCodes() {
        return currentUser().getRoleCode();
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
