package com.flx.netty.chat.gateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flx.netty.chat.gateway.constants.HeaderConstant;
import com.flx.netty.chat.gateway.entity.GatewayLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/14 11:41
 * @Description:
 */
@Slf4j
public class LogHelper {

    private final static ObjectMapper objectMapper = new ObjectMapper();



    /**
     * 根据MediaType获取字符集，如果获取不到，则默认返回<tt>UTF_8</tt>
     * @param mediaType MediaType
     * @return Charset
     */
    public static Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (Objects.nonNull(mediaType) && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    /**
     * 记录日志（后期可扩展为通过MQ将日志发送到ELK系统）
     * @param dto Log
     * @return Mono.empty()
     */
    public static Mono<Void> doRecord(GatewayLog dto) {
        log.info(JsonUtils.toJsonMsg(dto));
        return Mono.empty();
    }

    /**
     * 从HttpHeaders获取请求开始时间
     * 要求请求头中必须要有参数{@link HeaderConstant#REQUEST_START_TIME_KEY}，否则将返回当前时间戳
     * @param headers HttpHeaders请求头
     * @return 开始时间时间戳（Mills）
     */
    public static long getStartTime(HttpHeaders headers) {
        String startTimeStr = headers.getFirst(HeaderConstant.REQUEST_START_TIME_KEY);
        return StringUtils.isNotBlank(startTimeStr) ? Long.parseLong(startTimeStr) : System.currentTimeMillis();
    }

    /**
     * 根据HttpHeaders请求头获取请求执行时间
     * 求请求头中必须要有参数{@link HeaderConstant#REQUEST_START_TIME_KEY}
     * @param headers HttpHeaders请求头
     * @return 请求执行时间
     */
    public static long getExecuteTime(HttpHeaders headers) {
        String startTimeStr = headers.getFirst(HeaderConstant.REQUEST_START_TIME_KEY);
        long startTime = StringUtils.isNotBlank(startTimeStr) ? Long.parseLong(startTimeStr) : System.currentTimeMillis();
        return System.currentTimeMillis() - startTime;
    }

    /**
     * 读取请求体内容
     * @param request ServerHttpRequest
     * @return 请求体
     */
    public static RequestBody getRequestBody(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        MediaType mediaType = headers.getContentType();
        if (isUploadFile(mediaType)) {
            return new RequestBody(false,"File");
        } else {
            String method = request.getMethodValue().toUpperCase();
            if (method.equalsIgnoreCase(HttpMethod.GET.name())) {
                MultiValueMap<String, String> queryParams = request.getQueryParams();
                if (!queryParams.isEmpty()) {
                    return new RequestBody(false,queryParams.toString());
                }else {
                    return new RequestBody(false, null);
                }
            } else {
                AtomicReference<String> bodyString = new AtomicReference<>();
                request.getBody().subscribe(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
                    DataBufferUtils.release(buffer);
                    bodyString.set(new String(bytes, getMediaTypeCharset(mediaType)));
                });
                return new RequestBody(true,bodyString.get());
            }
        }
    }

    /**
     * 判断是否是上传文件
     * @param mediaType MediaType
     * @return Boolean
     */
    public static boolean isUploadFile(@Nullable MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }
        return mediaType.equals(MediaType.MULTIPART_FORM_DATA)
                || mediaType.equals(MediaType.IMAGE_GIF)
                || mediaType.equals(MediaType.IMAGE_JPEG)
                || mediaType.equals(MediaType.IMAGE_PNG)
                || mediaType.equals(MediaType.MULTIPART_MIXED);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestBody{
        private boolean read;
        private String content;
    }

}
