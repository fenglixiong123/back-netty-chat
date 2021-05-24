package com.flx.netty.chat.admin.common.wrapper;

import com.flx.netty.chat.common.utils.StringUtils;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.netflix.ribbon.support.ResettableServletInputStreamWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.flx.netty.chat.admin.common.filter.AuthTokenCheckFilter.AUTH_TOKEN;
import static com.flx.netty.chat.openfeign.interceptor.feign.FeignTokenRequestInterceptor.AUTHORIZATION_HEADER;
import static com.flx.netty.chat.openfeign.interceptor.feign.FeignTokenRequestInterceptor.BEARER_TOKEN_TYPE;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/21 17:35
 * @Description: 由于http的request不能直接修改头等信息，所以需要包装
 * request.getInputStream()来获取流来得到body中的信息，可以达到预期效果，但是流的获取只能获取一次，之后再获取就获取不到了
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    @Getter
    private String body;

    /**
     * 可以重复读取的流
     */
    private final ResettableServletInputStreamWrapper inputStream;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //读取请求中body内容，request失效，需要重新包装
        body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        this.inputStream = new ResettableServletInputStreamWrapper(body.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 修改请求中的header
     */
    @Override
    public String getHeader(String name) {
        String superHeader = super.getHeader(name);
        if(AUTHORIZATION_HEADER.equals(name) && StringUtils.isBlank(superHeader)){
            return String.format("%s %s", BEARER_TOKEN_TYPE, AUTH_TOKEN.get());
        }
        return super.getHeader(name);
    }

    @Override
    public ServletInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
