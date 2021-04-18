package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:43
 * @Description:
 */
@Import(value = SwaggerConfiguration.class)
public class SwaggerAutoConfiguration {
}
