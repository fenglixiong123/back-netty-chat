package com.flx.netty.chat.admin.common.except.element;


import com.flx.netty.chat.admin.common.except.BaseException;
import com.flx.netty.chat.common.enums.ErrorMsgEnum;

/**
 * @Author Fenglixiong
 * @Create 2018.11.14 17:17
 * @Description
 **/
public class AccessForbiddenException extends BaseException {

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum);
    }

}
