package com.flx.netty.chat.auth.console.security.listener;

import com.flx.netty.chat.security.entity.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 17:33
 * @Description: 登录成功的事件
 */
@Slf4j
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * 认证成功的事件
     * 可能会有多个事件：
     * 1.通过用户名密码校验身份的事件源（UsernamePasswordAuthenticationToken）
     * 2.根据access_token校验是否有效token的的事件源（OAuth2Authentication）
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if (!(event.getSource() instanceof UsernamePasswordAuthenticationToken)) {
            return;
        }
        Object principal = event.getAuthentication().getPrincipal();
        //这里还有oAuth2的客户端认证的事件，需要做一个判断
        //这个方式稍微有一点麻烦，不止是登陆事件，一些其他的事件，包括自定义的filter也可能进入该方法
        //所以需要这些判断保证只有用户登录成功才进入这里。
        if(principal instanceof CustomUserDetails){
            CustomUserDetails user = (CustomUserDetails)principal;
            Long userId = user.getUserId();
            log.info("++++++++++++++ {} login success ! ",user.getUsername());
        }
    }

}
