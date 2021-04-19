package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.RedisConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 1:43
 * @Description
 **/
@Import(value = RedisConfiguration.class)
public class RedisAutoConfiguration {

}
