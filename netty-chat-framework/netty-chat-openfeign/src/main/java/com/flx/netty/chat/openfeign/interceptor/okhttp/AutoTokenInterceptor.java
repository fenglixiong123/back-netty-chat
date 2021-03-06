package com.flx.netty.chat.openfeign.interceptor.okhttp;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.common.utils.http.BootUtils;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import com.flx.netty.chat.openfeign.property.AuthProperties;
import com.flx.netty.chat.openfeign.utils.OkUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.flx.netty.chat.common.utils.http.OkUtils.postJSON;
import static com.flx.netty.chat.openfeign.constants.FeignConstant.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 15:16
 * @Description: 全自动获取token，可以自动登录，使用默认的super账号进行登录
 */
@Slf4j
public class AutoTokenInterceptor implements Interceptor {

    @Autowired
    private AuthProperties authProperties;

    /**
     * 请求token
     */
    private static AtomicReference<String> AUTH_TOKEN = new AtomicReference<>();

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        //如果token为空则需要获得
        if(StringUtils.isBlank(AUTH_TOKEN.get())){
            log.info("Request url = {} token is blank,ready to load token from sso !",chain.request().url());
            try {
                loadAuthToken();
            } catch (Exception e) {
                return OkUtils.killRequestAndReturn(request,500,"LoadAuthToken ERROR",e.getMessage());
            }
        }
        request = request.newBuilder().header(AUTHORIZATION_HEADER,String.format("%s %s", BEARER_TOKEN_TYPE, AUTH_TOKEN.get())).build();
        //开始发起请求
        Response response = chain.proceed(request);
        //获得请求后的响应
        String contentType = response.header("content-type");
        if (StringUtils.containsIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
            ResponseBody responseBody = response.body();
            if(responseBody!=null){
                String body = responseBody.string();
                if(body.length()<2000 && body.contains("Invalid access token")){
                    try {
                        AUTH_TOKEN.set(null);
                        loadAuthToken();
                    } catch (Exception e) {
                        log.error("Token invalid,ReLoad error : {} ",e.getMessage());
                    }
                    if(StringUtils.isNotBlank(AUTH_TOKEN.get())) {
                        return chain.proceed(request.newBuilder().header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, AUTH_TOKEN.get())).build());
                    }else {
                        return OkUtils.buildErrorResponse(response, "503", "Re load token error !");
                    }
                }else {
                    return OkUtils.buildResponse(response,body,contentType);
                }
            }
        }
        return response;

    }

    /**
     * 远程通过认证服务器获取token
     */
    private synchronized void loadAuthToken() throws Exception{

        if(StringUtils.isNotBlank(AUTH_TOKEN.get())){
            return;
        }

        Map<String,Object> query = new HashMap<>();
        query.put("username","super");
        query.put("password","e46a9a9eb2f44eaf91e4c71ae0864e39");
        query.put("grant_type","password");
        query.put("client_id","netty-chat-admin");
        query.put("client_secret","56cffc32f8864431a4cec23cd1c6812e");

        BootUtils.HttpClient<JSONObject> httpClient = BootUtils.createClient();
        JSONObject result = httpClient
                .setUrl(authProperties.getSsoUrl())
                .setResponseType(JSONObject.class)
                .setQuerie(query)
                .build()
                .execute();
        Object accessToken = result.get(ACCESS_TOKEN);
        if(Objects.isNull(accessToken)){
            log.error("Get access token from authServer error : {}",result.toString());
            throw new Exception(result.toString());
        }
        AUTH_TOKEN.set(accessToken.toString());
        log.info("Get authToken successful,token = {}",accessToken);

    }

}
