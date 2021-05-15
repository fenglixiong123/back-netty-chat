package com.flx.netty.chat.gateway.filter;

import com.flx.netty.chat.gateway.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 17:21
 * @Description: 获取返回值进行记录
 */
@Slf4j
//@Component
public class ResponseFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            //获取response的 返回数据
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = serverHttpResponse.bufferFactory();
            HttpStatus statusCode = serverHttpResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(serverHttpResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        // 判断服务返回的数据类型进行拦截，根据自己的业务进行修改
                        if (APPLICATION_JSON.isCompatibleWith(getDelegate().getHeaders().getContentType())) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                                DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                                DataBuffer join = dataBufferFactory.join(dataBuffers);
                                byte[] content = new byte[join.readableByteCount()];
                                join.read(content);
                                DataBufferUtils.release(join);
                                return bufferFactory.wrap(handleResponse(content));
                            }));
                        } else {
                            return chain.filter(exchange);
                        }
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("ResponseFilter error : {}", e.getMessage());
            return chain.filter(exchange);
        }

    }

    /**
     * 处理响应内容
     * @param content 响应内容
     */
    private byte[] handleResponse(byte[] content) {
        String responseStr = new String(content, StandardCharsets.UTF_8);
        log.info("Gateway response : {}",responseStr);
        return content;
    }

    /**
     * 负数可以拦截请求响应
     */
    @Override
    public int getOrder() {
        //-1 is response write filter, must be called before that
        return OrderConstant.ORDER_RESPONSE_FILTER;
    }

}
