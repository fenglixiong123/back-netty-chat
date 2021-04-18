package com.flx.netty.chat.common.utils.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 可以随时随地获取request，response对象
 */
@Slf4j
public class ServletUtils {

    /**
     * 获取当前请求对象
      * @return
     */
    public static HttpServletRequest getRequest(){

        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder .getRequestAttributes()))
                .getRequest();
    }

    /**
     * 获取当前响应对象
     * @return
     */
    public static HttpServletResponse getResponse(){

        return ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getResponse();
    }

    /**
     * 获取当前Session
     * @return
     */
    public static HttpSession getSession(){

        return getRequest().getSession();
    }

    /**
     * 获取当前属性
     * @return
     */
    public static Object getAttribute(String name){
        return getRequest().getAttribute(name);
    }

    /**
     * 获取当前请求参数
     * @return
     */
    public static Object getParameter(String name){
        return getRequest().getParameter(name);
    }

    /**
     * 获取当前URL
     * @return
     */
    public static Object getURI(){
        return getRequest().getRequestURI();
    }

}
