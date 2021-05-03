package com.flx.netty.chat.login.console.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 23:42
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGrantedAuthority implements GrantedAuthority {

    private String code;

    @Override
    public String getAuthority() {
        return code;
    }
}
