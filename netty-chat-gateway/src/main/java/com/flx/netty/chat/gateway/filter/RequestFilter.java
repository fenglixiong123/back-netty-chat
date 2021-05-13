package com.flx.netty.chat.gateway.filter;

import com.flx.netty.chat.gateway.config.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 16:16
 * @Description:
 */
@Slf4j
@Component
public class RequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("Gateway request : ");
        URI uri = exchange.getRequest().getURI();
        log.info("  +++++URI = "+uri.toString());

        HttpMethod method = exchange.getRequest().getMethod();
        log.info("  +++++Method = "+method);

        InetSocketAddress address =exchange.getRequest().getRemoteAddress();
        log.info("  +++++Address = {}:{}",address.getAddress().getHostAddress(),address.getPort());

        return chain.filter(exchange);
    }

    /**
     * 数字越小优先级越高
     * @return 拦截器顺序
     */
    @Override
    public int getOrder() {
        return OrderConstant.ORDER_REQUEST_FILTER;
    }
}
