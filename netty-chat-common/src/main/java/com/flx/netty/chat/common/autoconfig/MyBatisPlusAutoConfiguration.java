package com.flx.netty.chat.common.autoconfig;


import com.flx.netty.chat.common.config.MybatisPlusConfiguration;
import com.flx.netty.chat.common.mybatis.common.TableFieldAlias;
import org.springframework.context.annotation.Import;

/**
 * @Author Fenglixiong
 * @Create 2020/8/4 0:55
 * @Description
 **/
@Import(value = {MybatisPlusConfiguration.class, TableFieldAlias.class})
public class MyBatisPlusAutoConfiguration {

}
