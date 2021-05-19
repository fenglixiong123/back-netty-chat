package com.flx.netty.chat.admin.common.except;


import com.flx.netty.chat.common.enums.ErrorMsgEnum;

/**
 * @Author Fenglixiong
 * @Create 2018.11.14 17:19
 * @Description
 **/
public class BaseException extends RuntimeException {

    private ErrorMsgEnum errorMsgEnum;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum.getMessage());
        this.errorMsgEnum = errorMsgEnum;
    }
}
