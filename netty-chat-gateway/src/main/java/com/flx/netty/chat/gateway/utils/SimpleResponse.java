package com.flx.netty.chat.gateway.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

/**
 * @Author Fenglixiong
 * @Create 2021/5/15 3:05
 * @Description
 **/
@Data
public class SimpleResponse<E> {

    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

    /**
     * 返回状态
     */
    private boolean success;

    /**
     * 返回代码
     */
    private String code;

    /**
     * 返回错误信息
     */
    private String message;

    /**
     * 返回结果
     */
    private E data;


    private SimpleResponse(){
        this.success = true;
    }

    private SimpleResponse(boolean success, String code, String msg, E data) {
        this.success = success;
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public SimpleResponse<E> success(String code, String message, E data) {
        this.success = true;
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public SimpleResponse<E> error(String code, String message, E data) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public static SimpleResponse<String> success(){
        return new SimpleResponse<>(true, "200","正常返回","操作成功");
    }

    public static <E> SimpleResponse<E> success(E data){
        return new SimpleResponse<>(true,"200","正常返回",data);
    }

    public static <E> SimpleResponse<E> success(String message, E data){
        return new SimpleResponse<>(true,"200",message,data);
    }


    public static SimpleResponse<String> error() {
        return new SimpleResponse<>(false,"500","系统错误","亲，系统出错了哦~");
    }

    public static SimpleResponse<String> error(String message) {
        return new SimpleResponse<>(false,"500",message,"亲，系统出错了哦~");
    }

    public static SimpleResponse<String> error(String code, String message) {
        return new SimpleResponse<>(false,code,message,null);
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
    
}
