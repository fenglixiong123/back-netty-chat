package com.flx.netty.chat.common.annotion;

import java.lang.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/12 15:18
 * @Description: 用来标识数据库字段名
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ColumnName {

    String value() default "";

}
