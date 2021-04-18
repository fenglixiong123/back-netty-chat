package com.flx.netty.chat.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author Fenglixiong
 * @Create 2018.11.20 17:07
 * @Description
 **/
public class JsonUtils {

    public static String toJsonMsg(Object o){
        if(o==null){
            return null;
        }
        return JSONObject.toJSONString(o);
    }

}
