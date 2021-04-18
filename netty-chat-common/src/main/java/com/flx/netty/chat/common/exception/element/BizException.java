package com.flx.netty.chat.common.exception.element;

import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.common.exception.BaseException;

/**
 * @Author Fenglixiong
 * @Create 2018.11.10 14:06
 * @Description 自定义业务异常类
 **/
public class BizException extends BaseException {

    public BizException(String message) {
        super(message);
    }

    public BizException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum);
    }
}
