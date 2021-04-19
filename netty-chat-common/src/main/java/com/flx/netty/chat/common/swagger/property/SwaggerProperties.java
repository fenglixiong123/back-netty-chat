package com.flx.netty.chat.common.swagger.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Fenglixiong
 * @Create 2018.11.07 17:36
 * @Description Swagger基本信息的一些配置
 *
 * >>>让配置文件生效方式：
 *
 * 1.直接注解方式
 *      a.@Componet //可以被Spring IOC管理
 *      b.@ConfigurationProperties({SwaggerInfoProperties.class}) //配置文件注解
 *
 * 2.配置类方式
 *      a.properties类加注解@ConfigurationProperties(prefix = "flx.swagger")
 *      b.项目配置类上加@EnableConfigurationProperties({SwaggerInfoProperties.class})
 *
 * >>>使用方式：
 *
 * @Service
 * class HelloService{
 *      @Autowired
 *      private SwaggerInfoProperties config;
 * }
 *
 **/
@Data
@ConfigurationProperties(prefix = "flx.swagger")
public class SwaggerProperties {

    private String groupName = "中国稀有物种网";

    private String basePackage = "";

    private String author = "fenglixiong";

    private String homepage = "https://github.com/fenglixiong123";

    private String email = "fenglixiong123@163.com";

    private String title = "接口文档画面";

    private String description = "智能化接口文档系统";

    private String version = "V2.0.0";

    private String license = "apache open source";

    private String licenseUrl = "http://www.baidu.com";

    private String termsOfServiceUrl = "https://www.fenglixiong.com.cn";

}
