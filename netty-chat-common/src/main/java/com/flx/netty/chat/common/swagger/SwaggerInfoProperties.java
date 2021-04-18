package com.flx.netty.chat.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Fenglixiong
 * @Create 2018.11.07 17:36
 * @Description Swagger基本信息的一些配置
 **/
@Data
@ConfigurationProperties(prefix = "flx.swagger")
public class SwaggerInfoProperties {

    private String groupName = "中国稀有物种网";

    private String basePackage = "";

    private String author = "fenglixiong";

    private String homepage = "https://github.com/fenglixiong123";

    private String email = "fenglixiong123@163.com";

    private String title = "接口文档画面";

    private String description = "swagger ui for China website";

    private String version = "1.0.0 RELEASE";

    private String license = "None";

    private String licenseUrl = "";

    private String termsOfServiceUrl = "https://www.fenglixiong.com.cn";

}
