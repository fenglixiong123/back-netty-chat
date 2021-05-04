package com.flx.netty.chat.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 16:44
 * @Description: 配置资源服务客户端
 * 自身虽然是资源服务端，但是如果调用其他微服务，那么自身也是服务客户端
 * 实现应用与应用之间的相互调用（通过Feign来实现），访问被auth2保护起来的资源
 */
@Slf4j
@Configuration
@EnableOAuth2Client
@EnableConfigurationProperties
@PropertySource(value = "classpath:/security.properties")
public class OAuth2ClientConfig {

    /**
     * 受保护资源的配置信息（来源于application.properties）
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails(){
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public OAuth2FeignRequestInterceptor oauth2FeignRequestlnterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(),
                this.clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(){
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }


}
