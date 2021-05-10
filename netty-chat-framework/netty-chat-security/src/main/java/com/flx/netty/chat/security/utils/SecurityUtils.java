package com.flx.netty.chat.security.utils;

import com.alibaba.fastjson.JSON;
import com.flx.netty.chat.security.entity.CurrentUser;
import com.flx.netty.chat.security.entity.SimpleAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class SecurityUtils {

    /**
     * 用于获得当前用户信息
     */
    public static CurrentUser currentUser() {
        return JSON.parseObject(JSON
                .parseObject(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()))
                .getJSONObject("userAuthentication").getJSONObject("details").getJSONObject("principal").toJSONString(),
                CurrentUser.class);
    }

    /**
     *
     * 用于获得当前用户的角色id
     */
    public static List <Integer> getRoleIds() {
        return currentUser().getRoleIds();
    }

    /**
     *
     * 用于获得当前权限列表
     */
    public static Set<SimpleAuthority> getGrantedAuthoritys() {
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
        return currentUser().getParams().get(key);
    }
}
