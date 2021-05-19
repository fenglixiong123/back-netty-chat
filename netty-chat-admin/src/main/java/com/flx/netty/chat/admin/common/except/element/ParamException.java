package com.flx.netty.chat.admin.common.except.element;


import com.flx.netty.chat.admin.common.except.BaseException;
import com.flx.netty.chat.common.enums.ErrorMsgEnum;

/**
 * @Author Fenglixiong
 * @Create 2018.11.16 16:52
 * @Description
 **/
public class ParamException extends BaseException {

    public ParamException(String message) {
        super(message);
    }

    public ParamException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum);
    }

}
