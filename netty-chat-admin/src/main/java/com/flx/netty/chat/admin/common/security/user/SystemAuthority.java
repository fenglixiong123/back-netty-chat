package com.flx.netty.chat.admin.common.security.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/4 12:17
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
public class SystemAuthority implements GrantedAuthority {

    /**
     * 角色或者权限
     */
    @Setter
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
