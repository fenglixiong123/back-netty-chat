package com.flx.netty.chat.gateway.filter;

import com.flx.netty.chat.gateway.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
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
    public int getOrder() {
        return OrderConstant.ORDER_LOG_RESPONSE_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try{

        }catch (Exception e){
            log.error("LogResponseFilter error : {}",e.getMessage());
        }
        return null;
    }


}
