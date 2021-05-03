package com.flx.netty.chat.plugin.plugins.exception.element;

import com.flx.netty.chat.common.enums.ErrorMsgEnum;
import com.flx.netty.chat.plugin.plugins.exception.BaseException;

/**
 * @Author: Fenglixiong
 * @Date: 2020/5/26 18:51
 * @Description:
 */
public class RedisException extends BaseException {

    public RedisException(String message) {
        super(message);
    }

    public RedisException(ErrorMsgEnum errorMsgEnum) {
        super(errorMsgEnum);
    }
}
