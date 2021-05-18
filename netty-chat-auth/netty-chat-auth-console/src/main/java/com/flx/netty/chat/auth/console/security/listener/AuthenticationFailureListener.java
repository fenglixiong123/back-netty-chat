package com.flx.netty.chat.auth.console.security.listener;

import com.flx.netty.chat.security.entity.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 18:29
 * @Description:
 */
@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {

        if (!(event.getSource() instanceof UsernamePasswordAuthenticationToken)) {
            return;
        }
        CustomUserDetails user = (CustomUserDetails) event.getAuthentication().getPrincipal();
        String username = user.getUsername();
        log.info("++++++++++++++ {} login error : {}",username,event.getException().getMessage());

    }

}
