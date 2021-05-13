package com.flx.netty.chat.gateway.filter;

import com.flx.netty.chat.gateway.config.constants.HeaderConstant;
import com.flx.netty.chat.gateway.config.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 19:05
 * @Description: 请求日志拦截器
 * https://blog.csdn.net/u012500848/article/details/104966647
 */
@Slf4j
@Component
public class LogRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();
        try{
            ServerHttpRequest request = exchange.getRequest();
            String requestId = UUID.randomUUID().toString();
            Consumer<HttpHeaders> headersConsumer = httpHeaders -> {
                String headerRequestId = request.getHeaders().getFirst(HeaderConstant.REQUEST_ID);
            };
        }catch (Exception e){
            log.error("LogRequestFilter error : {}",e.getMessage());
        }

        return null;
    }

    @Override
    public int getOrder() {
        return OrderConstant.ORDER_LOG_REQUEST_FILTER;
    }

}
