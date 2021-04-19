package com.flx.netty.chat.common.swagger;

import com.flx.netty.chat.common.swagger.property.SwaggerProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @Author Fenglixiong
 * @Create 2021/4/20 0:47
 * @Description
 **/
@Slf4j
@Component
public class SwaggerService {

    @Autowired
    private SwaggerProperties swaggerInfoProperty;

    /**
     * 创建Docket信息
     * @return
     */
    public Docket buildDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfoProperty.getGroupName())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .apiInfo(getApiInfo());
        String basePackage = swaggerInfoProperty.getBasePackage();
        ApiSelectorBuilder apiSelectorBuilder;
        if(StringUtils.isBlank(basePackage)){
            apiSelectorBuilder = docket.select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        }else {
            apiSelectorBuilder = docket.select()
                    .apis(RequestHandlerSelectors.basePackage(basePackage));
        }
        log.info("*************************************************");
        log.info("*                                               *");
        log.info("*               Swagger UI Success              *");
        log.info("*                                               *");
        log.info("*************************************************");
        log.info(">>>>>>>>>>Swagger UI 启动啦!<<<<<<<<<<<<");
        return apiSelectorBuilder.build();
    }

    /**
     * 基本信息
     * @return
     */
    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title(swaggerInfoProperty.getTitle())
                .description(swaggerInfoProperty.getDescription())
                .license(swaggerInfoProperty.getLicense())
                .licenseUrl(swaggerInfoProperty.getLicenseUrl())
                .termsOfServiceUrl(swaggerInfoProperty.getTermsOfServiceUrl())
                .version(swaggerInfoProperty.getVersion())
                .contact(new Contact(
                        swaggerInfoProperty.getAuthor(),
                        swaggerInfoProperty.getHomepage(),
                        swaggerInfoProperty.getEmail()))
                .build();
    }

}
