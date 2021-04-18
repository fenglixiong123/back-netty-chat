package com.flx.netty.chat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/15 17:38
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult {

    /**
     * 状态码
     */
    private int statusCode;
    /**
     * 返回结果
     */
    private String message;

    public HttpResult(String message) {
        this.statusCode = 0;
        this.message = message;
    }
}
