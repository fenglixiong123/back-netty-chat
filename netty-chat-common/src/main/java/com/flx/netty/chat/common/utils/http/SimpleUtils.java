package com.flx.netty.chat.common.utils.http;

import com.flx.netty.chat.common.constants.WebConstant;
import com.flx.netty.chat.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2018.11.09 23:21
 * @Description
 **/
@Slf4j
public class SimpleUtils {

    /**
     * GET请求
     */
    public static String doGet(String url, Map<String,Object> paramMap) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if(CollectionUtils.isNotEmpty(paramMap)){
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String,Object> entry : paramMap.entrySet()){
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
            }
            url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs), WebConstant.UTF_8);
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
        return EntityUtils.toString(httpResponse.getEntity());
    }

    /**
     * POST请求
     */
    public static String doPost(String url, Map<String, Object> paramMap) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (paramMap != null) {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, WebConstant.UTF_8);
            formEntity.setContentType("application/json");
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity());


    }

}
