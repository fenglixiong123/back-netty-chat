package com.flx.netty.chat.gateway.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/13 16:25
 * @Description:
 */
@SuppressWarnings("all")
public class RequestUtils {

    public static Map<String,List<String>> getParamMap(ServerHttpRequest request) {
        Map<String,List<String>> paramMap = new HashMap<>();
        MultiValueMap<String, String> paramNames = request.getQueryParams();
        for (String paramName : paramNames.keySet()) {
            paramMap.put(paramName, paramNames.get(paramName));
        }
        return paramMap;
    }

    public static Map<String,List<String>> getHeaderMap(ServerHttpRequest request) {
        Map<String,List<String>> headerMap = new HashMap<>();
        HttpHeaders httpHeaders = request.getHeaders();
        for (String keyName : httpHeaders.keySet()) {
            headerMap.put(keyName, httpHeaders.get(keyName));
        }
        return headerMap;
    }


}
