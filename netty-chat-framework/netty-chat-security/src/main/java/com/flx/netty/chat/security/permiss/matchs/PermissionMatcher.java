package com.flx.netty.chat.security.permiss.matchs;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 16:39
 * @Description:
 */
public interface PermissionMatcher {

    /**
     * 匹配权限
     * @param attributes 请求所需要的权限
     * @param authorities 用户拥有的权限
     */
    boolean match(Collection<ConfigAttribute> attributes, Collection<? extends GrantedAuthority> authorities);

}
