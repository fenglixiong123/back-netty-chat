package com.flx.netty.chat.common.annotion.enable;

import com.flx.netty.chat.common.autoconfig.ExceptionAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2019/12/5 19:22
 * @Description: 在项目启动类中使用@EnableExceptionHandle注解，即可启用异常捕获功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ExceptionAutoConfiguration.class})
@Documented
public @interface EnableExceptionHandler {

}
