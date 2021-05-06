package com.flx.netty.chat.auth.console.security.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 18:24
 * @Description:
 */
@Slf4j
@Component
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return super.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return super.matches(charSequence,s);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return super.upgradeEncoding(encodedPassword);
    }

}
