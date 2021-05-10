package com.flx.netty.chat.auth.console.security.user.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/4 12:17
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthority implements GrantedAuthority {

    /**
     * 角色或者权限
     */
    private String code;

    @Override
    public String getAuthority() {
        return code;
    }
}
