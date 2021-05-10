package com.flx.netty.chat.security.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/10 11:50
 * @Description:
 */
@Data
public class CurrentUser {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色列表
     */
    private List< Integer > roleIds;

    /**
     * 用户属性
     */
    private Map< String, Object > params;

    private String password;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户权限
     */
    private Set<SimpleAuthority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

}
