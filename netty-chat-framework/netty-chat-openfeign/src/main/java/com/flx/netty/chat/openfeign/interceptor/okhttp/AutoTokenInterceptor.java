package com.flx.netty.chat.openfeign.interceptor.okhttp;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.common.utils.system.PropertyUtils;
import com.flx.netty.chat.openfeign.utils.OkUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.flx.netty.chat.common.utils.http.OkUtils.postJSON;
import static com.flx.netty.chat.openfeign.constants.FeignConstant.ACCESS_TOKEN;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 15:16
 * @Description: 全自动获取token，可以自动登录，使用默认的super账号进行登录
 */
@Slf4j
public class AutoTokenInterceptor implements Interceptor {

    /**
     * 请求token
     */
    private static AtomicReference<String> AUTH_TOKEN = new AtomicReference<>();

    private static final String SSO_URL = "http://"+ PropertyUtils.get("sso.ip","127.0.0.1") +":8001/oauth/token";


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        //如果token为空则需要获得
        if(StringUtils.isBlank(AUTH_TOKEN.get())){
            log.info("Request url = {} token is blank,ready to load token from sso !",chain.request().url());
            try {
                loadAuthToken(SSO_URL);
            } catch (Exception e) {
                log.error("LoadAuthToken error : {}",e.getMessage());
                return OkUtils.buildErrorResponse("503","LoadAuthToken post error !");
            }
        }
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
                        loadAuthToken(SSO_URL);
                    } catch (Exception e) {
                        log.error("Re LoadAuthToken error : {} ",e.getMessage());
                    }
                    return OkUtils.buildErrorResponse("503","Re load token error !");
                }
            }
        }
        return response;

    }

    /**
     * 远程通过认证服务器获取token
     */
    private synchronized void loadAuthToken(String url) throws Exception{

        if(StringUtils.isNotBlank(AUTH_TOKEN.get())){
            return;
        }

        Map<String,String> query = new HashMap<>();
        query.put("username","master");
        query.put("password","master123");
        query.put("grant_type","password");
        query.put("client_id","netty-chat-admin");
        query.put("client_secret","123456");

        JSONObject result = postJSON(url, query, null);
        Object accessToken = result.get(ACCESS_TOKEN);
        if(Objects.isNull(accessToken)){
            log.error("Get access token from authServer error : {}",result.toString());
            throw new Exception("Access token is null !");
        }
        AUTH_TOKEN.set(accessToken.toString());
        log.info("Get authToken successful,token = {}",accessToken);

    }

}
