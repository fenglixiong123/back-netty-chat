package com.flx.netty.chat.auth.console.security.user.provider;

import com.flx.netty.chat.auth.console.security.user.password.CustomPasswordEncoder;
import com.flx.netty.chat.auth.console.security.user.service.CustomUserDetailsService;
import com.flx.netty.chat.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author Fenglixiong
 * @Create 2021/5/13 2:20
 * @Description
 * auth.userDetailsService(userDetailService)//配置获取用户信息
 *                 .passwordEncoder(passwordEncoder);//配置密码加密方式,添加用户加密的时候请也用这个加密
 **/
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        Object credential = authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new BadCredentialsException("用户名不能为空！");
        }
        if(Objects.isNull(credential)){
            throw new BadCredentialsException("密码不能为空！");
        }
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
        }catch (Exception e){
            throw new BadCredentialsException("用户名不存在！");
        }
        if (!user.isEnabled()) {
            throw new BadCredentialsException("用户已经禁用！");
        }
        if (!user.isAccountNonLocked()) {
            throw new BadCredentialsException("用户已经锁定！");
        }
        if (!user.isAccountNonExpired()) {
            throw new BadCredentialsException("用户已经过期！");
        }
        if (!user.isCredentialsNonExpired()) {
            throw new BadCredentialsException("用户已经过期！");
        }
        if(!passwordEncoder.matches(credential.toString(),user.getPassword())){
            throw new BadCredentialsException("用户密码错误！");
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
