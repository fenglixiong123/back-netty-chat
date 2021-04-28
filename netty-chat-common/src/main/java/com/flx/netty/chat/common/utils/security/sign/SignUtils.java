package com.flx.netty.chat.common.utils.security.sign;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;

import static com.flx.netty.chat.common.constants.SecurityConstant.MD5;
import static com.flx.netty.chat.common.constants.SecurityConstant.SHA1;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/28 18:42
 * @Description: 签名工具类
 *
 * 提供md5，sha1，hmac等抽象摘要算法
 *
 */
public class SignUtils {



    public static String getParamSign(Map<String, String> params, String secret){
        return getParamSign(params,secret,MD5);
    }

    /**
     * 一种对请求参数进行签名的方式
     * 参数key要按照字母表顺序排序
     * 签名方法：md5(secret+key_value_key_value+secret)；
     * @return 返回签名后的结果
     */
    public static String getParamSign(Map<String, String> params, String secret ,String method){
        if(StringUtils.isBlank(method)){
            method = "md5";
        }
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key)) {
                query.append(key).append("_");
            }
            if(StringUtils.isNotBlank(value)){
                query.append(value);
            }
        }
        query.append(secret);
        return getSign(query.toString(),method);
    }

    /**
     * 获取md5签名
     * @param content
     * @return
     */
    public static String getMd5Sign(String content){
        return getSign(content,MD5);
    }

    /**
     * 获取sha1签名
     * @param content
     * @return
     */
    public static String getSha1Sign(String content){
        return getSign(content,SHA1);
    }

    /**
     * 获取mac签名
     * @param content
     * @param secretKey
     * @return
     */
    public static String getHmacSign(String content,String secretKey){
        return HmacUtils.getSign(content,secretKey);
    }

    public static String getHmacSign(String content,String secretKey,String macKey){
        return HmacUtils.getSign(content,secretKey,macKey);
    }

    /**
     * 根据方法获取签名
     * @param content
     * @param method
     * @return
     */
    public static String getSign(String content,String method){
        switch (method.toLowerCase()){
            case MD5:
                return Md5Utils.getMD5(content);
            case SHA1:
                return Sha1Utils.getSha1(content);
        }
        return null;
    }

}
