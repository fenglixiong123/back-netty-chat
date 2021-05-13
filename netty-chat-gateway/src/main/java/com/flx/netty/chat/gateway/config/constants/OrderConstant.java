package com.flx.netty.chat.gateway.config.constants;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 18:30
 * @Description:
 */
public class OrderConstant {

    /**
     * 日志请求拦截器
     */
    public final static int ORDER_LOG_REQUEST_FILTER = 1;

    /**
     * 日志响应拦截器顺序
     */
    public final static int ORDER_LOG_RESPONSE_FILTER = -2;

    /**
     * 请求拦截器顺序
     */
    public final static int ORDER_REQUEST_FILTER = 1;

    /**
     * 响应拦截器顺序
     */
    public final static int ORDER_RESPONSE_FILTER = -99;

}
