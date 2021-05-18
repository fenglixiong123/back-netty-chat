package com.flx.netty.chat.auth.console.security.user.permission.impl;

import com.flx.netty.chat.auth.console.security.user.permission.PermissionSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 19:51
 * @Description:
 */
@Slf4j
@Component
public class RedisPermissionSource implements PermissionSource {

    //可以将权限存储在redis中，然后客户端直接去redis中进行访问，登出的时候redis清空权限

    @Override
    public void loadPermission() throws Exception {

    }

    @Override
    public void clearPermission() throws Exception {

    }

}
