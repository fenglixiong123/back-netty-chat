package com.flx.netty.chat.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.common.constants.WebConstant;
import com.flx.netty.chat.common.utils.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/15 17:03
 * @Description:
 */
@Slf4j
@NoArgsConstructor
public class HttpUtils{

    public static void main(String[] args) throws Exception {

        JSONObject params = new JSONObject();
        params.put("video_id","60_8be004799c424688949704814ea0d16d");
        params.put("state",3+"");
        params.put("attr_tags","");
        params.put("msg","");
        HttpClient httpClient = HttpUtils.createClient();
        httpClient
                .setUrl("http://127.0.0.1:8023/api/basic/tenant/getCurrentTenantCode")
                .setHeader("type","utf-8")
                .setQuery("name","admin")
                .setJsonBody(params.toJSONString())
                .build();
        HttpResult result = httpClient.post();
        System.out.println(result);

    }

    /**
     * Httpclient?????????
     */
    private static PoolingHttpClientConnectionManager pcm;

    static {
        pcm = new PoolingHttpClientConnectionManager();
        pcm.setMaxTotal(50);//??????????????????????????????
        pcm.setDefaultMaxPerRoute(50);//???????????????????????????????????????2
    }

    /**
     * ????????????????????????????????????
     */
    public static class HttpClient{

        /**
         * HttpClient??????
         */
        private CloseableHttpClient httpClient = null;
        /**
         * ??????????????????
         */
        private int connectTimeout = 120000;
        /**
         * ????????????????????????????????????
         */
        private int connectionRequestTimeout = 10000;
        /**
         * ????????????????????????
         */
        private int socketTimeout = 300000;
        /**
         * ????????????
         */
        private RequestConfig config = null;

        /**
         * ??????url
         */
        private String url;

        /**
         * ????????????
         */
        private Map<String,String> queryMap = new HashMap<>();

        /**
         * ?????????
         */
        private Map<String,String> headerMap = new HashMap<>();

        /**
         * ????????????
         */
        private String jsonBody = "{}";

        /**
         * ????????????????????????
         */
        public HttpClient setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * ??????????????????????????????
         */
        public HttpClient setConnectRequestTimeout(int connectRequestTimeout) {
            this.connectionRequestTimeout = connectRequestTimeout;
            return this;
        }

        /**
         * ????????????????????????
         */
        public HttpClient setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        /**
         * ???????????????
         */
        public HttpClient setHeader(String name, String value) {
            this.headerMap.put(name,value);
            return this;
        }

        /**
         * ???????????????(??????)
         */
        public HttpClient setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * ???????????????(??????)
         */
        public HttpClient setHeader(Map<String, String> headMap) {
            this.headerMap.putAll(headMap);
            return this;
        }

        /**
         * ??????????????????
         */
        public HttpClient setQuery(String name, String value) {
            this.queryMap.put(name,value);
            return this;
        }

        /**
         * ??????????????????(??????)
         */
        public HttpClient setQuery(Map<String, String> paramMap) {
            this.queryMap.putAll(paramMap);
            return this;
        }

        /**
         * ?????????????????????
         */
        public HttpClient setJsonBody(String jsonBody) {
            this.jsonBody = jsonBody;
            return this;
        }

        /**
         * ??????????????????
         */
        public HttpClient build() {
            if(this.config == null) {
                this.config = RequestConfig.custom()
                        .setConnectTimeout(this.connectTimeout)
                        .setConnectionRequestTimeout(this.connectionRequestTimeout)
                        .setSocketTimeout(this.socketTimeout)
                        .build();
            }
            if(this.httpClient == null) {
                this.httpClient = HttpClients.custom()
                        .setConnectionManager(pcm)
                        .build();
            }
            return this;
        }

        /**
         * get??????
         */
        public HttpResult get() throws Exception{
            return HttpUtils.get(url,queryMap,headerMap,httpClient,config);
        }

        /**
         * post??????
         */
        public HttpResult post() throws Exception{
            return HttpUtils.post(url,queryMap,headerMap,jsonBody,httpClient,config);
        }

    }

    /**
     * ??????Client??????
     */
    public static HttpClient createClient(){
        return new HttpClient();
    }


    /**
     * http get ??????
     */
    public static HttpResult get(String url,Map<String,String> queryMap,Map<String,String> headerMap,CloseableHttpClient httpClient,RequestConfig config) throws Exception {
        HttpResult httpResult = new HttpResult();
        URI uri = getPerfectUri(url,queryMap);
        if (uri != null) {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(config);
            if (CollectionUtils.isNotEmpty(headerMap)) {
                httpGet.setHeaders(toHeaderArray(headerMap));
            }
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                return getHttpResult(response, url, httpGet);
            } catch (Exception e) {
                httpGet.abort();
                httpResult.setMessage(e.getMessage());
                log.error("??????http GET????????????????????? url = " + url, e);
            }
        }
        return httpResult;
    }

    /**
     * http post ??????
     */
    public static HttpResult post(String url,Map<String,String> queryMap,Map<String,String> headerMap,String jsonBody,CloseableHttpClient httpClient,RequestConfig config) throws Exception{
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        if (!CollectionUtils.isEmpty(headerMap)) {
            httpPost.setHeaders(toHeaderArray(headerMap));
        }
        if (!CollectionUtils.isEmpty(queryMap)) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(toNamePairList(queryMap), WebConstant.UTF_8));
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
                httpPost.abort();
                return new HttpResult(e.getMessage());
            }
        }
        if (StringUtils.isNotBlank(jsonBody)) {
            try {
                httpPost.setEntity(new StringEntity(jsonBody, ContentType.create("application/json", "UTF-8")));
            } catch (UnsupportedCharsetException e) {
                log.error(e.getMessage());
                httpPost.abort();
                return new HttpResult(e.getMessage());
            }
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return getHttpResult(response, url, httpPost);
        } catch (Exception e) {
            log.error(e.getMessage());
            httpPost.abort();
            return new HttpResult(e.getMessage());
        }
    }

    /**
     * ??????header??????
     * @param headerMap
     * @return
     */
    private static Header[] toHeaderArray(Map<String,String> headerMap){
        List<Header> headers = new ArrayList<>();
        for (Entry<String, String> header : headerMap.entrySet()) {
            headers.add(new BasicHeader(header.getKey(), header.getValue()));
        }
        Header[] header = new Header[headers.size()];
        return headers.toArray(header);
    }

    /**
     * ??????nameValuePair
     * @param queryMap
     * @return
     */
    private static List<NameValuePair> toNamePairList(Map<String,String> queryMap){
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Entry<String, String> param : queryMap.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return nameValuePairs;
    }

    /**
     * ??????????????????url??????
     */
    private static URI getPerfectUri(String url,Map<String,String> queryMap) throws Exception{
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            return uriBuilder.setParameters(toNamePairList(queryMap)).build();
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new Exception("Build perfect url error !");
        }
    }

    /**
     * ?????????????????????
     */
    private static HttpResult getHttpResult(CloseableHttpResponse response, String url, HttpUriRequest request) {
        HttpResult httpResult = new HttpResult();
        int status = response.getStatusLine().getStatusCode();
        httpResult.setStatus(status);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                httpResult.setMessage(EntityUtils.toString(entity, WebConstant.UTF_8));
                EntityUtils.consume(entity);//????????????
            } catch (Exception e) {
                log.error("??????http???????????????????????????", e);
                request.abort();
            }
        }
        if (status != 200) {
            httpResult.setMessage("HttpClient status code :" + status + "  request url===" + url);
            request.abort();
        }
        return httpResult;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class HttpResult {

        /**
         * ?????????
         */
        private int status;
        /**
         * ????????????
         */
        private String message;

        public HttpResult(String message) {
            this.status = 0;
            this.message = message;
        }

    }
    
    
}
