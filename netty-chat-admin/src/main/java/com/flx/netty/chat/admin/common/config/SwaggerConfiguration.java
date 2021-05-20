package com.flx.netty.chat.admin.common.config;

import com.flx.netty.chat.admin.common.property.SwaggerProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Fenglixiong
 * @Create 2021/5/20 3:01
 * @Description
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerConfiguration implements InitializingBean {

    @Autowired
    private SwaggerProperties swaggerInfoProperty;

    /**
     * 创建Docket信息
     * @return
     */
    @Bean
    public Docket docket(){
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

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>>>>>>>>Swagger Successful<<<<<<<<<<<<");
    }
}
