package com.flx.netty.chat.gateway.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 18:52
 * @Description:
 */
@Data
public class GatewayLog {

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

    /**日志类型*/
    private String logType;
    /**请求协议*/
    private String schema;
    /**访问实例*/
    private String targetServer;
    /**请求时间*/
    private Date requestTime;
    /**响应时间*/
    private Date responseTime;
    /**执行时间*/
    private long executeTime;

}
