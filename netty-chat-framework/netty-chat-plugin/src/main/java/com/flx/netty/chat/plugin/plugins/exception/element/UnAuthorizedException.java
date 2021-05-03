package com.flx.netty.chat.plugin.plugins.exception.element;


import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.plugin.plugins.exception.BaseException;

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
