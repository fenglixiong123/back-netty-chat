package com.flx.netty.chat.common.plugins.exception.element;


import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.common.plugins.exception.BaseException;

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
