package com.flx.netty.chat.common.annotion.enable;

import com.flx.netty.chat.common.autoconfig.FlywayAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2019/12/5 19:22
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({FlywayAutoConfiguration.class})
@Documented
public @interface EnableFlyway {

}
