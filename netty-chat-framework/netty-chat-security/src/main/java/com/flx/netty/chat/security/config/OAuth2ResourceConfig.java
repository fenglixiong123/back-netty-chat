package com.flx.netty.chat.security.config;

import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.security.property.SecurityClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.List;

import static com.flx.netty.chat.common.utils.ArrayUtils.list2Array;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/3 15:42
 * @Description: 配置资源服务,微服务本身就是资源服务
 * ResourceServerConfig是比SecurityConfig的优先级低的
 */
@Slf4j
@Configuration
@EnableResourceServer //开启了Resource Server功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启了方法级别的保护
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    /**
     * 默认服务资源ID
     */
    private final static String DEFAULT_RESOURCE_ID = "flx-oauth2-resource";

    @Autowired//自定义授权认证异常处理
    private AuthenticationDeniedHandler oAuth2AuthenticationEntryPoint;
    @Autowired//自定义权限异常处理
    private PermissionDeniedHandler accessDeniedHandler;
    @Autowired//权限控制的配置属性
    private SecurityClientProperties securityProperties;
    @Autowired//Redis连接工厂
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 设置token存储，这一点配置要与授权服务器相一致
     */
    @Bean
    public RedisTokenStore tokenStore(){
        log.info("[Resource] Token store in redis !");
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 配置资源服务id
     * 配置Token的校验方式
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String resourceId = securityProperties.getResourceId();
        if(StringUtils.isBlank(resourceId)){
            resourceId = DEFAULT_RESOURCE_ID;
        }
        resources.stateless(true);//无状态
        resources.resourceId(resourceId);//资源Id
        resources.tokenStore(tokenStore());//配置Token的校验方式
    }

    /**
     * 配置资源访问规则
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //所有资源可以访问
        if(securityProperties.isPermitAll()){
            http.authorizeRequests().anyRequest().permitAll();
        }else {
            List<String> whitePermits = securityProperties.getWhitePermits();
            List<String> whiteResources = securityProperties.getWhiteResources();
            String[] permits = list2Array(whitePermits);
            String[] resources = list2Array(whiteResources);
            log.info("========>whitePermits = {}", JsonUtils.toJsonMsg(permits));
            log.info("========>whiteResources = {}", JsonUtils.toJsonMsg(resources));
            http
                //设置一个拒绝访问的提示
                .csrf().disable().exceptionHandling()
                    .authenticationEntryPoint(oAuth2AuthenticationEntryPoint)//自定义授权认证异常处理
                    .accessDeniedHandler(accessDeniedHandler)//自定义权限异常处理
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)//
                .and()
                    .authorizeRequests()//需要授权的访问地址
                        .antMatchers(permits).permitAll()//允许一些URL可以访问
                        .antMatchers(resources).permitAll()//允许一些资源可以访问
                //剩下的必须经过授权访问
                .and()
                    .authorizeRequests()
                        .anyRequest().authenticated();
        }

    }

}
