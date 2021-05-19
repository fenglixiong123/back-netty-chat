package com.flx.netty.chat.admin.common.except.element;


import com.flx.netty.chat.admin.common.except.BaseException;
import com.flx.netty.chat.common.enums.ErrorMsgEnum;

/**
 * @Author Fenglixiong
 * @Create 2018.11.14 17:23
 * @Description
 **/
public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum);
    }
}
