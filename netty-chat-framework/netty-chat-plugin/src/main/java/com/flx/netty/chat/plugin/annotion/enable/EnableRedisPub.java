package com.flx.netty.chat.plugin.annotion.enable;

import com.flx.netty.chat.plugin.autoconfig.RedisPubAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/19 0:50
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisPubAutoConfiguration.class})
@Documented
public @interface EnableRedisPub {

}
