package com.flx.netty.chat.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Fenglixiong
 * @Create 2018.11.07 17:36
 * @Description Swagger基本信息的一些配置
 *
 **/
@Data
@ConfigurationProperties(prefix = "flx.swagger",ignoreInvalidFields = true)
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
