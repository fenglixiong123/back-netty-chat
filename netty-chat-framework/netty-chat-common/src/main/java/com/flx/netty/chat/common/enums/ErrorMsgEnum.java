package com.flx.netty.chat.common.enums;

/**
 * @Author Fenglixiong
 * @Create 2018.11.10 10:49
 * @Description
 **/
public enum ErrorMsgEnum {

    //正常返回的枚举
    SUCCESS(true, "200","正常返回", "操作成功"),

    // 系统错误，50开头
    SYS_ERROR(false, "500", "系统错误", "亲，系统出错了哦~"),
    PARAM_CHECK(false,"300","字段校验异常","字段校验出现问题了哦~"),
    PARAM_MISSING(false, "400", "少参异常", "请求缺少参数了哦~"),
    PARAM_INVALID(false, "600", "传参异常", "传参出现异常了哦~"),
    JSON_CONVERT_ERROR(false, "700", "JSON解析异常", "JSON解析出现异常了哦~"),
    REDIS_ERROR(false, "800", "Redis操作异常", "Redis操作出现异常了哦~"),
    DATA_NO_COMPLETE(false, "5002", "数据填写不完整，请检查", "数据填写不完整，请检查");


    private boolean success;
    private String code;
    private String message;
    private String userMsg;

    ErrorMsgEnum(boolean success, String code, String message , String userMsg) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.userMsg = userMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }
}
