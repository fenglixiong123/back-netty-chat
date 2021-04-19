package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.FlywayConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:37
 * @Description:
 */
@Import(value = FlywayConfiguration.class)
public class FlywayAutoConfiguration {
}
