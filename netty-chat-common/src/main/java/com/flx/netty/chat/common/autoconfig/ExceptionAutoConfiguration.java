package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.ExceptionConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:34
 * @Description:
 */
@Import(value = ExceptionConfiguration.class)
public class ExceptionAutoConfiguration {
}
