package com.flx.netty.chat.plugin.annotion.enable;

import com.flx.netty.chat.plugin.autoconfig.RedisAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2019/12/5 19:22
 * @Description: 提供redis基础功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisAutoConfiguration.class})
@Documented
public @interface EnableRedis {

}
