package com.flx.netty.chat.auth.console.security.user;

import com.flx.netty.chat.auth.api.enums.UserStateEnum;
import com.flx.netty.chat.auth.console.security.model.CustomGrantedAuthority;
import com.flx.netty.chat.auth.crud.entity.WebUser;
import com.flx.netty.chat.auth.crud.manager.UserManager;
import com.flx.netty.chat.common.utils.date.DateUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 17:55
 * @Description: 通过用户名查询用户数据
 *
 */
@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            //从数据库查询用户
            log.info("loadUserByUsername username = {}",username);
            WebUser user = userManager.get("userName", username);
            log.info("loadUserByUsername user = {}", JsonUtils.toJsonMsg(user));
            boolean enabled = user.getState().equals(UserStateEnum.effective.name());
            boolean expired = user.getState().equals(UserStateEnum.expired.name())|| (user.getExpireTime() != null && DateUtils.beforeNow(user.getExpireTime()));
            boolean locked = user.getState().equals(UserStateEnum.locked.name());
            return new User(user.getUserName(), user.getPassword(), enabled, !expired, true, !locked, getAuthorities());
        } catch (Exception e) {
            log.error("loadUserByUsername error username = {},message = {} !",username,e.getMessage());
            throw new UsernameNotFoundException("User [" + username + "] not exist !");
        }

    }

    /**
     * Security才不管你是角色，还是权限。它只比对字符串
     *
     * 比如它有个表达式hasRole("ADMIN")。那它实际上查询的是用户权限集合中是否存在字符串"ROLE_ADMIN"。
     * 如果你从角色表中取出用户所拥有的角色时不加上"ROLE_"前缀，那验证的时候就匹配不上了。
     *
     * 所以角色信息存储的时候可以没有"ROLE_"前缀，但是包装成GrantedAuthority对象的时候必须要有。
     *
     * @return 权限角色集合
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //设置两个角色加一个权限
        return Arrays.asList(
                new CustomGrantedAuthority("ROLE_USER"),
                new CustomGrantedAuthority("ROLE_ADMIN")
        );
    }

}
