package com.flx.netty.chat.common.utils.http;

import com.flx.netty.chat.common.utils.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/21 16:53
 * @Description:
 */
public class BaseUtils {

    /**
     * 获取默认的头信息
     */
    public static Map<String,String> getDefaultHeaders() {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Encoding", "UTF-8");
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        return headerMap;
    }

    /**
     * 处理url，将query数据代入
     */
    public static String getPerfectUrl(String url, Map<String, String> queries) throws Exception {
        if(StringUtils.isBlank(url)){
            throw new Exception("url is empty !");
        }
        StringBuilder sb = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(queries)) {
            int i = 0;
            for (Map.Entry<String, String> query : queries.entrySet()){
                if(i==0){
                    sb.append("?").append(query.getKey()).append("=").append(query.getValue());
                }else {
                    sb.append("&").append(query.getKey()).append("=").append(query.getValue());
                }
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * 转为 a=1&b=2&c=3...&n=n 的形式
     */
    public static String getUrlQueries(Map<String, Object> queries) {
        Iterator<String> it = queries.keySet().iterator();
        StringBuilder urlQueries = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = "";
            Object object = queries.get(key);
            if (object != null) {
                if (!StringUtils.isEmpty(object.toString())) {
                    value = object.toString();
                }
            }
            urlQueries.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return urlQueries.substring(1);
    }


}
