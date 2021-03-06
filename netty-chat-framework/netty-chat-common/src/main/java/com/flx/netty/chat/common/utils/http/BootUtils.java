package com.flx.netty.chat.common.utils.http;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

import static com.flx.netty.chat.common.utils.http.BaseUtils.getDefaultHeaders;
import static com.flx.netty.chat.common.utils.http.BaseUtils.getUrlQueries;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/15 16:52
 * @Description:
 * // 出现中文乱码现象
 * String json = HttpUtil.get(url, null);
 * json = new String(json.getBytes("ISO-8859-1"), "UTF-8");
 */
@Slf4j
public class BootUtils {

    public static void main(String[] args) {
        HttpClient<String> httpClient = BootUtils.createClient();
        String result = httpClient
                .setUrl("http//127.0.0.1:8023/api/hello")
                .setMethod(HttpMethod.POST)
                .setResponseType(String.class)
                .build()
                .execute();
        System.out.println("result = "+result);
    }

    /**
     * RestAPI 调用器
     */
    @Getter
    private final static RestTemplate restTemplate = new RestTemplate();

    public static <T> T get(String url,Class<T> c) {
        return get(url, null,null,null,c);
    }

    public static <T> T get(String url, String params,Class<T> c) {
        return get(url,null, null, params, c);
    }

    public static <T> T get(String url, Map<String,Object> queries,Class<T> c) {
        return get(url, null,queries, null,c);
    }

    public static <T> T get(String url, Map<String,Object> queries, String params,Class<T> c) {
        return get(url,null, queries, params, c);
    }

    public static <T> T get(String url,Map<String,String> headers, Map<String,Object> queries, String params,Class<T> c) {
        return request(url, HttpMethod.GET,headers, queries, params,c);
    }


    public static <T> T post(String url,Class<T> c) {
        return post(url, null,null, null,c);
    }

    public static <T> T post(String url, String params,Class<T> c) {
        return post(url, null,null, params,c);
    }

    public static <T> T post(String url, Map<String,Object> queries,Class<T> c) {
        return post(url, null,queries, null,c);
    }

    public static <T> T post(String url, Map<String,Object> queries, String params,Class<T> c) {
        return post(url, null,queries, params,c);
    }

    public static <T> T post(String url,Map<String,String> headers, Map<String,Object> queries, String params,Class<T> c) {
        return request(url, HttpMethod.POST,headers, queries, params,c);
    }


    public static <T> T put(String url,Class<T> c) {
        return put(url, null,null,null, c);
    }

    public static <T> T put(String url, String params,Class<T> c) {
        return put(url, null,null, params,c);
    }

    public static <T> T put(String url, Map<String,Object> queries,Class<T> c) {
        return put(url, null,queries, null,c);
    }

    public static <T> T put(String url, Map<String,Object> queries, String params,Class<T> c) {
        return put(url, null,queries, params,c);
    }

    public static <T> T put(String url,Map<String,String> headers, Map<String,Object> queries, String params,Class<T> c) {
        return request(url, HttpMethod.PUT,headers ,queries, params,c);
    }


    public static <T> T delete(String url,Class<T> c) {
        return delete(url, null,null, null,c);
    }

    public static <T> T delete(String url, String params,Class<T> c) {
        return delete(url, null, null, params, c);
    }

    public static <T> T delete(String url, Map<String,Object> queries, Class<T> c) {
        return delete(url, null, queries, null, c);
    }

    public static <T> T delete(String url, Map<String,Object> queries, String params,Class<T> c) {
        return delete(url, null, queries, params, c);
    }


    public static <T> T delete(String url, Map<String,String> headers, Map<String,Object> queries, String params,Class<T> c) {
        return request(url, HttpMethod.DELETE, headers, queries, params, c);
    }

    /**
     * 发送请求
     * @param url 请求地址
     * @param method 请求方式
     * @param headers 请求头 可空
     * @param queries 请求url参数 可空
     * @param requestBody 请求body参数 可空
     * @param responseType 返回类型
     * @return ResponseEntity<responseType>
     */
    public static <T> T request(String url, HttpMethod method,
                                                Map<String,String> headers, Map<String,Object> queries, String requestBody,
                                                Class<T> responseType) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("Request url is blank !");
        }
        if (method == null) {
            throw new RuntimeException("Request method is blank !");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        if(headers==null){
            headers = getDefaultHeaders();
        }
        headers.forEach(httpHeaders::add);
        if (queries != null) {
            url += ("?" + getUrlQueries(queries));
        }
        HttpEntity<String> request = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, method, request, responseType).getBody();
    }


    @Data
    public static class HttpClient<T>{
        private String url;
        private HttpMethod method;
        private Map<String,String> headers;
        private Map<String,Object> queries;
        private String requestBody;
        private Class<T> responseType;
        
        public HttpClient<T> setUrl(String url){
            this.url = url;
            return this;
        }

        public HttpClient<T> setMethod(HttpMethod method){
            this.method = method;
            return this;
        }

        public HttpClient<T> setHeader(Map<String,String> headers){
            this.headers = headers;
            return this;
        }

        public HttpClient<T> setQuerie(Map<String,Object> queries){
            this.queries = queries;
            return this;
        }

        public HttpClient<T> setRequestBody(String requestBody){
            this.requestBody = requestBody;
            return this;
        }

        public HttpClient<T> setResponseType(Class<T> responseType){
            this.responseType = responseType;
            return this;
        }

        /**
         * 构建请求对象
         */
        public HttpClient<T> build(){
            Objects.requireNonNull(this.url);
            if(method==null){
                method = HttpMethod.POST;
            }
            if(headers==null){
                headers = getDefaultHeaders();
            }
            if(requestBody==null){
                requestBody = "{}";
            }
            return this;
        }

        /**
         * 执行
         * @return 返回执行结果
         */
        public T execute() {
            return request(url, method, headers, queries,
                    requestBody, responseType);
        }

    }

    /**
     * 创建客户端
     * @param <T>
     * @return
     */
    public static <T> HttpClient<T> createClient(){
        return new HttpClient<>();
    }

    
}
