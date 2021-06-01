package com.flx.netty.chat.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/6 18:14
 * @Description:
 */
public class PasswordUtils {

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String code = UUID.randomUUID().toString().replace("-","");
        System.out.println(code);
        String pass = encode("super123");

        System.out.println(pass);

        System.out.println(matches("netty-chat-front-secret",pass));

    }

    public static String encode(String source){
        return passwordEncoder.encode(source);
    }

    public static boolean matches(String source,String secret){
        return passwordEncoder.matches(source,secret);
    }

}
