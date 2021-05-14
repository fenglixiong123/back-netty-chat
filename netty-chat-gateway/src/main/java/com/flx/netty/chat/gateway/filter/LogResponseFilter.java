package com.flx.netty.chat.gateway.filter;

import com.flx.netty.chat.gateway.config.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 19:05
 * @Description: 响应日志拦截器
 */
@Slf4j
//@Component
public class LogResponseFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return OrderConstant.ORDER_LOG_RESPONSE_FILTER;
    }

}
