package com.flx.netty.chat.common.config;

import com.flx.netty.chat.common.swagger.SwaggerInfoProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Fenglixiong
 * @Create 2018.11.07 17:37
 * @Description 主要初始化了一个开发框架给定了一些默认配置(来自properties包里面)的swagger服务
 * 如果在配置文件中配置flx.swagger.basePackage就会默认以这个包来进行扫描swagger文件
 **/
@Slf4j
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerInfoProperties.class})
@ConditionalOnJava(value = JavaVersion.EIGHT)
public class SwaggerConfiguration {

    @Autowired
    private SwaggerInfoProperties swaggerInfoProperty;

    @Bean
    public Docket createRestApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfoProperty.getGroupName())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .apiInfo(apiInfo());
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
    private ApiInfo apiInfo(){

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

    @GetMapping(value = "/swagger")
    public ModelAndView home() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @GetMapping(value = "/swagger2")
    public DeferredResult<ModelAndView> ui2() {
        DeferredResult<ModelAndView> result = new DeferredResult<>();
        result.setResult(new ModelAndView("redirect:/doc.html"));
        return result;
    }

}
