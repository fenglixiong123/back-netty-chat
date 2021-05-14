package com.flx.netty.chat.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.gateway.config.constants.HeaderConstant;
import com.flx.netty.chat.gateway.config.constants.OrderConstant;
import com.flx.netty.chat.gateway.entity.LogEntity;
import com.flx.netty.chat.gateway.utils.CryptoTool;
import com.flx.netty.chat.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.flx.netty.chat.gateway.utils.LogHelper.isUploadFile;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 19:05
 * @Description: 请求日志拦截器
 * 参考类：ModifyRequestBodyGatewayFilterFactory
 * https://blog.csdn.net/u012500848/article/details/104966647
 */
@Slf4j
@Component
public class LogRequestFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return OrderConstant.ORDER_LOG_REQUEST_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        try{

            LogEntity logEntity = new LogEntity();
            long startTime = System.currentTimeMillis();
            ServerHttpRequest oldRequest = exchange.getRequest();
            String paramSign = oldRequest.getQueryParams().getFirst("sign");
            String headSign = oldRequest.getHeaders().getFirst("sign");
            String sign = paramSign != null ? paramSign : headSign;

            HttpHeaders headers = oldRequest.getHeaders();
            URI uri = oldRequest.getURI();
            String ip = Objects.requireNonNull(oldRequest.getRemoteAddress()).getHostName();
            Integer port = Objects.requireNonNull(oldRequest.getRemoteAddress()).getPort();
            String query = uri.getQuery();
            String url = uri.toString();
            String contentType = headers.getFirst("Content-Type");
            String method = oldRequest.getMethodValue().toUpperCase();
            MediaType mediaType = headers.getContentType();
            Assert.notNull(mediaType,"mediaType is null !");

            logEntity.setIp(ip);
            logEntity.setPort(port);
            logEntity.setUrl(url);
            logEntity.setQuery(query);
            logEntity.setContentType(contentType);
            logEntity.setMethod(method);

            //重新写header
            AtomicReference<String> requestId = new AtomicReference<>(UUID.randomUUID().toString());
            Consumer<HttpHeaders> headerConsumer = httpHeaders -> {
                String headerRequestId = oldRequest.getHeaders().getFirst(HeaderConstant.REQUEST_ID);
                if(StringUtils.isBlank(headerRequestId)){
                    httpHeaders.set(HeaderConstant.REQUEST_ID,requestId.get());
                }else {
                    requestId.set(headerRequestId);
                }
                httpHeaders.set(HeaderConstant.REQUEST_START_TIME_KEY, String.valueOf(startTime));
            };

            //重新创建请求
            ServerHttpRequest newRequest = oldRequest.mutate().headers(headerConsumer).build();

            //如果是上传文件请求
            if(isUploadFile(mediaType)){
                logEntity.setHeader(JsonUtils.toJsonMsg(headers));
                logEntity.setRequestBody("File");
                log.info("request will upload a file : {}",mediaType.toString());
                log.info("Request log = {}",JsonUtils.toJsonMsg(logEntity));
                return chain.filter(exchange.mutate().request(newRequest).build());
            }

            //如果是GET请求
            if(method.equalsIgnoreCase(HttpMethod.GET.name())) {
                logEntity.setHeader(JsonUtils.toJsonMsg(headers));
                verifySignature(query,sign);
                log.info("Request log = {}",JsonUtils.toJsonMsg(logEntity));
                return chain.filter(exchange.mutate().request(newRequest).build());

            //如果是非GET请求
            }else {

                //重新创建请求
                ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
                Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(originalBody -> {

                    //log.info(">>>>>>>>>OriginalBody:");
                    //log.info(originalBody);
                    logEntity.setRequestBody(originalBody);

                    //如果是json请求的话
                    if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {

                        //处理json请求体
                        String newBody = handleJsonBody(originalBody, sign);

                        return Mono.just(Objects.requireNonNull(newBody));

                    } else {
                        return Mono.just(originalBody);
                    }

                });

                BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(newRequest.getHeaders());
                httpHeaders.remove("Content-Length");//ContentType将会被重新计算然后设置到请求体中
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, httpHeaders);
                return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                    ServerHttpRequest decorator = this.decorate(exchange, httpHeaders, outputMessage);
                    logEntity.setHeader(JsonUtils.toJsonMsg(httpHeaders));
                    log.info("Request log = {}",JsonUtils.toJsonMsg(logEntity));
                    return chain.filter(exchange.mutate().request(decorator).build());
                })).onErrorResume(Mono::error);

            }

        }catch (Exception e){
            log.error("LogRequestFilter error : {}",e.getMessage());
            return chain.filter(exchange);
        }

    }

    /**
     * 重写请求
     * @param exchange 交换器
     * @param headers 头信息
     * @param outputMessage 输出信息
     * @return 新的请求
     */
    private ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @NotNull
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0L) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set("Transfer-Encoding", "chunked");
                }

                return httpHeaders;
            }

            @NotNull
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


    /**
     * 处理json请求体
     * @param reqBody
     * @return
     */
    private String handleJsonBody(String reqBody,String sign){
        JSONObject jsonObject = JSON.parseObject(reqBody);
        String newBody = reqBody;
        try{
            //1.验证签名
            verifySignature(reqBody,sign);
            //2.处理body内容
        }catch (Exception e){
            return null;
        }
        return newBody;
    }

    /**
     * 对body进行签名验证
     * @param body
     * @throws Exception
     */
    private void verifySignature(String body,String sign) throws Exception{

        //todo
        boolean signEnable = false;
        String salt = "abc123";
        if(signEnable){
            if(StringUtils.isBlank(sign)){
                throw new Exception("Sign is null !");
            }
            String md5 = CryptoTool.encodeMd5(body, salt);
            if(!md5.equals(sign)){
                throw new Exception("Sign verify error !");
            }
        }
    }



}
