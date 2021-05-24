package com.flx.netty.chat.openfeign.utils;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 16:31
 * @Description:
 */
public class OkUtils {

    public static Response buildResponse(Response response,String newBody,String contentType){
        return response.newBuilder()
                .body(ResponseBody.create(newBody,okhttp3.MediaType.parse(contentType)))
                .build();
    }

    public static Response buildErrorResponse(String message){
        return new Response.Builder()
                .body(ResponseBody.create(ResultResponse.error(message).toString(),okhttp3.MediaType.parse("application/json")))
                .build();
    }

    public static Response buildErrorResponse(String code,String message){
        return new Response.Builder()
                .body(ResponseBody.create(ResultResponse.error(code,message).toString(),okhttp3.MediaType.parse("application/json")))
                .build();
    }

}
