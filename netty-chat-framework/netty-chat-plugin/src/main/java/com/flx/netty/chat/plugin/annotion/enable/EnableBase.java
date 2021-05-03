package com.flx.netty.chat.plugin.annotion.enable;

import com.flx.netty.chat.plugin.autoconfig.CrossAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 19:19
 * @Description: 一个注解解决所有
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CrossAutoConfiguration.class})
@Documented
@EnableCross        //加入跨域处理
@EnableFlyway       //加入Flyway数据库
@EnableMyBatis      //加入Mybatis持久化
@EnableSwagger      //加入Swagger文档
@EnableException    //加入全局异常处理
public @interface EnableBase {

}
