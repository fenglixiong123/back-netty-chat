package com.flx.netty.chat.common.utils.result;

import com.flx.netty.chat.common.constants.WebConstant;
import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Fenglixiong
 * @Create 2018.11.09 23:23
 * @Description
 **/
@Data
public class ResultResponse<E> {


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


    private ResultResponse(){
        this.success = true;
    }

    private ResultResponse(boolean success, String code, String msg, E data) {
        this.success = success;
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResultResponse<E> success(String code, String message, E data) {
        this.success = true;
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public ResultResponse<E> error(String code, String message, E data) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public static ResultResponse<String> success(){
        return new ResultResponse<>(true, ErrorMsgEnum.SUCCESS.getCode(),ErrorMsgEnum.SUCCESS.getMessage(),ErrorMsgEnum.SUCCESS.getUserMsg());
    }

    public static <E> ResultResponse<E> success(E data){
        return new ResultResponse<>(true,ErrorMsgEnum.SUCCESS.getCode(),ErrorMsgEnum.SUCCESS.getMessage(),data);
    }

    public static <E> ResultResponse<E> success(String message, E data){
        return new ResultResponse<>(true,ErrorMsgEnum.SUCCESS.getCode(),message,data);
    }


    public static ResultResponse<String> error() {
        return new ResultResponse<>(false,ErrorMsgEnum.SYS_ERROR.getCode(),ErrorMsgEnum.SYS_ERROR.getMessage(),ErrorMsgEnum.SYS_ERROR.getUserMsg());
    }

    public static ResultResponse<String> error(String message) {
        return new ResultResponse<>(false,ErrorMsgEnum.SYS_ERROR.getCode(),message,ErrorMsgEnum.SYS_ERROR.getUserMsg());
    }

    public static ResultResponse<String> error(String code, String message) {
        return new ResultResponse<>(false,code,message,null);
    }

    public static ResultResponse<String> error(ErrorMsgEnum errorMsgEnum) {
        return new ResultResponse<>(false,errorMsgEnum.getCode(),errorMsgEnum.getMessage(),errorMsgEnum.getUserMsg());
    }

    public static ResultResponse<String> error(ErrorMsgEnum errorMsgEnum, String userMsg) {
        return new ResultResponse<>(false,errorMsgEnum.getCode(),errorMsgEnum.getMessage(),userMsg);
    }



    public static void printSuccess(HttpServletResponse response){
        printJson(response,true, ErrorMsgEnum.SUCCESS.getCode(),ErrorMsgEnum.SUCCESS.getMessage(),ErrorMsgEnum.SUCCESS.getUserMsg());
    }

    public static <E> void printSuccess(HttpServletResponse response,String message,E data){
        printJson(response,true,ErrorMsgEnum.SUCCESS.getCode(),message,data);
    }

    public static <E> void printSuccess(HttpServletResponse response,E data){
        printJson(response,true,ErrorMsgEnum.SUCCESS.getCode(),ErrorMsgEnum.SUCCESS.getMessage(),data);
    }

    public static void printError(HttpServletResponse response) {
        printJson(response,false,ErrorMsgEnum.SYS_ERROR.getCode(),ErrorMsgEnum.SYS_ERROR.getMessage(),ErrorMsgEnum.SYS_ERROR.getUserMsg());
    }

    public static void printError(HttpServletResponse response,String message) {
        printJson(response,false,ErrorMsgEnum.SYS_ERROR.getCode(),message,ErrorMsgEnum.SYS_ERROR.getUserMsg());
    }

    public static void printError(HttpServletResponse response,String code, String message) {
        printJson(response,false,code,message,null);
    }

    public static <E> void printError(HttpServletResponse response,String code, String message,E data) {
        printJson(response,false,code,message,data);
    }

    public static <E> void printJson(HttpServletResponse response,boolean success, String code, String message, E data) {
        response.setContentType(WebConstant.CONTENT_TYPE_JSON);
        try {
            response.getWriter().print(JsonUtils.toJsonMsg(new ResultResponse<>(success,code,message,data)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printHtml(HttpServletResponse response,String html) {
        response.setContentType(WebConstant.CONTENT_TYPE_HTML);
        try {
            response.getWriter().print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getResult(ResultResponse<T> response) throws Exception{
        if(response.isSuccess()) {
            return response.getData();
        }else {
            throw new Exception(response.getMessage());
        }
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
