package com.flx.netty.chat.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.flx.netty.chat.common.utils.http.BaseUtils.getDefaultHeaders;
import static com.flx.netty.chat.common.utils.http.BaseUtils.getPerfectUrl;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/10 16:20
 * @Description:
 */
@Slf4j
public class OkUtils {

    static class ApiConfig{
        final static int DEFAULT_CONNECT_TIMEOUT = 30;
        final static int DEFAULT_READ_TIMEOUT = 30;
        final static int DEFAULT_WRITE_TIMEOUT = 30;
    }

    /**
     * 获取OKHttpClient
     */
    public static OkHttpClient getOK(){
        return getOK(ApiConfig.DEFAULT_CONNECT_TIMEOUT, ApiConfig.DEFAULT_READ_TIMEOUT, ApiConfig.DEFAULT_WRITE_TIMEOUT);
    }

    public static OkHttpClient getOK(long connectTime,long readTime,long writeTime){
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(connectTime, TimeUnit.SECONDS)
                .readTimeout(readTime, TimeUnit.SECONDS)
                .writeTimeout(writeTime,TimeUnit.SECONDS)
                .build();
    }

    public static JSONObject getJSON(String url) throws Exception {
        return getJSON(url,null);
    }

    public static JSONObject getJSON(String url, Map<String, String> queries) throws Exception {
        return JSONObject.parseObject(getString(url,queries));
    }

    public static JSONObject postJSON(String url, Map<String, String> queries, String jsonData) throws Exception {
        return JSONObject.parseObject(postString(url,queries,jsonData));
    }

    public static <T> T get(String url, Class<T> z) throws Exception{
        return get(url,null,z);
    }

    public static <T> T get(String url, Map<String, String> queries, Class<T> z) throws Exception {
        return JSON.parseObject(getString(url,queries), z);
    }

    public static <T> T post(String url,Class<T> z) throws Exception{
        return post(url,null,z);
    }

    public static <T> T post(String url,String jsonData,Class<T> z) throws Exception{
        return post(url,null,jsonData,z);
    }

    public static <T> T post(String url,Map<String, String> queries,String jsonData,Class<T> z) throws Exception {
        return JSON.parseObject(postString(url,queries,jsonData), z);
    }

    public static String getString(String url) throws Exception {
        return getString(url,null);
    }

    public static String getString(String url, Map<String, String> queries) throws Exception {
        return getString(url,null,queries);
    }

    public static String getString(String url,Map<String,String> headerMap, Map<String, String> queries) throws Exception {
        return request(url,HttpMethod.GET,Optional.ofNullable(headerMap).orElse(getDefaultHeaders()),queries,null);
    }

    public static String postString(String url) throws Exception {
        return postString(url,null,null,null);
    }

    public static String postString(String url,String jsonData) throws Exception {
        return postString(url,null,jsonData);
    }

    public static String postString(String url,Map<String, String> queries) throws Exception {
        return postString(url,queries,null);
    }

    public static String postString(String url,Map<String, String> queries,String jsonData) throws Exception {
        return postString(url,null,queries,jsonData);
    }

    public static String postString(String url,Map<String,String> headerMap,Map<String, String> queries,String jsonData) throws Exception {
        return request(url,HttpMethod.POST,Optional.ofNullable(headerMap).orElse(getDefaultHeaders()),queries,jsonData);
    }

    /**
     * 返回字符串
     * 如果返回空指针或者找不到类
     * 解决方案：
     * 1.将create(jsonData, MediaType.parse("application/json; charset=utf-8"))两个参数位置互换
     * 2.如果传入json为空，则替换为"{}"
     * @param url 调用url路径
     * @param method 请求方法
     * @param headerMap
     *                  "Content-Type", "application/x-www-form-urlencoded"
     *                  application/json; charset=utf-8
     * @param queries url后面参数
     * @param jsonData json数据
     * @return 结果字符串
     * @throws Exception 异常
     */
    public static String request(String url, HttpMethod method, Map<String,String> headerMap, Map<String, String> queries, String jsonData)throws Exception{
        Objects.requireNonNull(url);
        Objects.requireNonNull(headerMap);
        Request request = null;
        RequestBody requestBody = RequestBody.create(Optional.ofNullable(jsonData).orElse("{}"), MediaType.parse("application/json; charset=utf-8"));
        switch (method){
            case GET :
                request = new Request.Builder().url(getPerfectUrl(url,queries)).headers(Headers.of(headerMap)).get().build();
                break;
            case POST:
                request = new Request.Builder().url(getPerfectUrl(url,queries)).headers(Headers.of(headerMap)).post(requestBody).build();
                break;
            case PUT:
                request = new Request.Builder().url(getPerfectUrl(url,queries)).headers(Headers.of(headerMap)).put(requestBody).build();
                break;
            case DELETE:
                request = new Request.Builder().url(getPerfectUrl(url,queries)).headers(Headers.of(headerMap)).delete(requestBody).build();
                break;
        }
        Objects.requireNonNull(request);
        Response response = getOK()
                .newCall(request)
                .execute();
        ResponseBody responseBody = response.body();
        return Optional.ofNullable(responseBody).map(e->{
            try {
                return e.string();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }).orElse(null);
    }

}
