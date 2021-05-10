package com.flx.netty.chat.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/10 11:54
 * @Description:
 */
@Data
public class SimpleAuthority implements GrantedAuthority {

    public String authority;

}
