package com.flx.netty.chat.admin.common.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Set;

/**
 * @Author Fenglixiong
 * @Create 2021/5/11 3:15
 * @Description
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SystemUserDetails implements UserDetails {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户权限
     */
    private Set<SystemAuthority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    /**
     * 用户属性
     */
    private Map< String, Object > infoMap;

}
