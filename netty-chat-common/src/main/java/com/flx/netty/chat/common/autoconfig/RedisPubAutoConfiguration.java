package com.flx.netty.chat.common.autoconfig;

import com.flx.netty.chat.common.config.RedisPubConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author Fenglixiong
 * @Create 2021/4/20 3:08
 * @Description
 **/
@Import(value = RedisPubConfiguration.class)
public class RedisPubAutoConfiguration {

}
