package com.flx.netty.chat.auth.console.security.user.permission;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 19:49
 * @Description: 次类用来缓存用户权限信息
 */
public interface PermissionSource {

    void loadPermission() throws Exception;

    void clearPermission() throws Exception;

}
