package com.flx.netty.chat.gateway.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/14 11:44
 * @Description:
 */
public class JsonUtils {

    public static String toJsonMsg(Object o){
        if(o==null){
            return null;
        }
        return JSONObject.toJSONString(o);
    }

}
