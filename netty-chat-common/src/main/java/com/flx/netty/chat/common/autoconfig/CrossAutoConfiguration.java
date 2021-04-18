package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.CrossConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:49
 * @Description:
 */
@Import(value = CrossConfiguration.class)
public class CrossAutoConfiguration {

}
