package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.flyway.FlywayConfigurationInitializer;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:37
 * @Description:
 */
@Import(value = FlywayConfigurationInitializer.class)
public class FlywayAutoConfiguration {
}
