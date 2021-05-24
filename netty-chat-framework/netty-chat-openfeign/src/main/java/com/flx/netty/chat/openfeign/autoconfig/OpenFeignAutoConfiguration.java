package com.flx.netty.chat.openfeign.autoconfig;

import com.flx.netty.chat.openfeign.config.AutoTokenConfiguration;
import com.flx.netty.chat.openfeign.config.ParamTokenConfiguration;
import com.flx.netty.chat.openfeign.property.AuthProperties;
import com.flx.netty.chat.openfeign.property.OkHttpProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/24 14:34
 * @Description:
 * havingValue : 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * matchIfMissing : 缺少该配置属性时是否可以加载。如果为true，没有该配置属性时也会正常加载；反之则不会生效
 */
@Configuration
@EnableConfigurationProperties(value = {AuthProperties.class, OkHttpProperties.class})
@Import({AutoTokenConfiguration.class, ParamTokenConfiguration.class})
@ConditionalOnProperty(prefix = "flx.feign.token",name = "enable",havingValue = "true",matchIfMissing = true)
public class OpenFeignAutoConfiguration {



}
