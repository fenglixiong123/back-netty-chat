package com.flx.netty.chat.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.flx.netty.chat.common.constants.WebConstant;
import com.flx.netty.chat.common.entity.HttpResult;
import com.flx.netty.chat.common.utils.CollectionUtils;
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

    public static void main(String[] args) {

        JSONObject params = new JSONObject();
        params.put("video_id","60_8be004799c424688949704814ea0d16d");
        params.put("state",3+"");
        params.put("attr_tags","");
        params.put("msg","");
        HttpUtils httpUtil = HttpUtils.createHttpClient()
                .setHeader("type","utf-8")
                .setParam("name","admin")
                .builder();
        String URL ="http://127.0.0.1:8023/api/basic/tenant/getCurrentTenantCode";
        HttpResult post = httpUtil.get(URL);
        System.out.println(post);

    }

    /**
     * Httpclient连接池
     */
    private static PoolingHttpClientConnectionManager pcm;
    /**
     * HttpClient连接
     */
    private CloseableHttpClient httpClient = null;
    /**
     * 连接超时时间
     */
    private int connectTimeout = 120000;
    /**
     * 从连接池获取连接超时时间
     */
    private int connectionRequestTimeout = 10000;
    /**
     * 获取数据超时时间
     */
    private int socketTimeout = 300000;
    /**
     * 请求配置
     */
    private RequestConfig requestConfig = null;

    /**
     * 请求参数以及请求头
     */
    private List<NameValuePair> nameValuePairs = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    private String requestParam = "";

    static {
        pcm = new PoolingHttpClientConnectionManager();
        pcm.setMaxTotal(50);//整个连接池最大连接数
        pcm.setDefaultMaxPerRoute(50);//每路由最大连接数，默认值是2
    }

    public HttpUtils(int connectTimeout, int socketTimeout) {
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    /**
     * 创建对象
     * @return
     */
    public static HttpUtils createHttpClient(){
        return new HttpUtils();
    }

    /**
     * 创建对象
     * @return
     */
    public static HttpUtils createHttpClient(int connectTimeout, int socketTimeout){
        return new HttpUtils(connectTimeout,socketTimeout);
    }

    /**
     * 创建httpUtil
     */
    public HttpUtils builder() {
        if(this.requestConfig == null) {
            this.requestConfig = RequestConfig.custom()
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
     * http get 请求
     */
    public HttpResult get(String url) {
        HttpResult httpResult = new HttpResult();
        URI uri = getUri(url);
        if (uri != null) {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            if (CollectionUtils.isNotEmpty(headers)) {
                Header[] header = new Header[headers.size()];
                httpGet.setHeaders(headers.toArray(header));
            }

            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                return getHttpResult(response, url, httpGet);
            } catch (Exception e) {
                httpGet.abort();
                httpResult.setMessage(e.getMessage());
                log.error("获取http GET请求返回值失败 url======" + url, e);
            }
        }
        return httpResult;
    }

    /**
     * http post 请求
     */
    public HttpResult post(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (!CollectionUtils.isEmpty(headers)) {
            Header[] header = new Header[headers.size()];
            httpPost.setHeaders(headers.toArray(header));
        }
        if (!CollectionUtils.isEmpty(nameValuePairs)) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, WebConstant.UTF_8));
            } catch (UnsupportedEncodingException e) {
                log.error("http post entity form error", e);
            }
        }
        if (!StringUtils.isEmpty(requestParam)) {
            try {
                httpPost.setEntity(new StringEntity(requestParam, WebConstant.UTF_8));
            } catch (UnsupportedCharsetException e) {
                log.error("http post entity form error", e);
            }
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return getHttpResult(response, url, httpPost);
        } catch (Exception e) {
            httpPost.abort();
            log.error("获取http POST请求返回值失败 url======" + url, e);
            return new HttpResult(e.getMessage());
        }
    }

    /**
     * 设置连接超时时间
     */
    public HttpUtils setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 设置获取连接超时时间
     */
    public HttpUtils setConnectRequestTimeout(int connectRequestTimeout) {
        this.connectionRequestTimeout = connectRequestTimeout;
        return this;
    }

    /**
     * 设置传输超时时间
     */
    public HttpUtils setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    /**
     * 设置请求头
     */
    public HttpUtils setHeader(String name, String value) {
        Header header = new BasicHeader(name, value);
        headers.add(header);
        return this;
    }

    /**
     * 设置请求头
     */
    public HttpUtils setHeaderMap(Map<String, String> headerMap) {
        for (Entry<String, String> param : headerMap.entrySet()) {
            Header header = new BasicHeader(param.getKey(), param.getValue());
            headers.add(header);
        }
        return this;
    }

    /**
     * 设置请求参数
     */
    public HttpUtils setParam(String name, String value) {
        nameValuePairs.add(new BasicNameValuePair(name, value));
        return this;
    }

    /**
     * 设置请求参数
     */
    public HttpUtils setParamMap(Map<String, String> paramMap) {
        for (Entry<String, String> param : paramMap.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return this;
    }

    /**
     * 设置字符串参数
     */
    public HttpUtils setStringParam(String requestParam) {
        this.requestParam = requestParam;
        return this;
    }

    private URI getUri(String url) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(nameValuePairs)) {
                uriBuilder.setParameters(nameValuePairs);
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("url 地址异常", e);
        }
        return uri;
    }

    /**
     * 获取请求返回值
     */
    private HttpResult getHttpResult(CloseableHttpResponse response, String url, HttpUriRequest request) {
        HttpResult httpResult = new HttpResult();
        int statusCode = response.getStatusLine().getStatusCode();
        httpResult.setStatusCode(statusCode);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                httpResult.setMessage(EntityUtils.toString(entity, WebConstant.UTF_8));
                EntityUtils.consume(entity);//释放连接
            } catch (Exception e) {
                log.error("获取http请求返回值解析失败", e);
                request.abort();
            }
        }
        if (statusCode != 200) {
            httpResult.setMessage("HttpClient status code :" + statusCode + "  request url===" + url);
            request.abort();
        }
        return httpResult;
    }
    
    
}
