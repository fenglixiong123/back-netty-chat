package com.flx.netty.chat.auth.console.security.user.password;

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

    /**
     * 加密
     * @param source
     * @return
     */
    @Override
    public String encode(CharSequence source) {
        return super.encode(source);
    }

    /**
     * 比较
     * @param source 源密码
     * @param secret 加密后的密码
     * @return
     */
    @Override
    public boolean matches(CharSequence source, String secret) {
        return super.matches(source,secret);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return super.upgradeEncoding(encodedPassword);
    }

}
