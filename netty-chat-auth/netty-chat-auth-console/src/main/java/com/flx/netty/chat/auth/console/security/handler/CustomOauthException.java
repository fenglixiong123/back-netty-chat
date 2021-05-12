package com.flx.netty.chat.auth.console.security.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Author Fenglixiong
 * @Create 2021/5/13 0:24
 * @Description 添加自定义异常类，指定json序列化方式
 **/
@JsonSerialize(using=CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private String data;

    public CustomOauthException(String message,String data) {
        super(message);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
