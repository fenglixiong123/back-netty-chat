package com.flx.netty.chat.gateway.entity;

import lombok.Data;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 18:52
 * @Description:
 */
@Data
public class LogEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * Ip地址
     */
    private String ip;

    private Integer port;

    private String url;
    private String method;
    private String query;
    private String header;
    private String contentType;
    private String requestBody;
    private String responseBody;
    private String logType;

}
