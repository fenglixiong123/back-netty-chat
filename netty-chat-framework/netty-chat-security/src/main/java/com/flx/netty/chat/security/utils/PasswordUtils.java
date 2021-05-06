package com.flx.netty.chat.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/6 18:14
 * @Description:
 */
public class PasswordUtils {

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {

        String pass = encode("admin123");

        System.out.println(pass);

        System.out.println(matches("admin123",pass));

    }

    public static String encode(String source){
        return passwordEncoder.encode(source);
    }

    public static boolean matches(String source,String secret){
        return passwordEncoder.matches(source,secret);
    }

}
