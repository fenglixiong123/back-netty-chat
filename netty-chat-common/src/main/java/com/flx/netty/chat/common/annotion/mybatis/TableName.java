package com.flx.netty.chat.common.annotion.mybatis;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 15:21
 * @Description: 用来标识数据库表名
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableName {

    String value() default "";

}
