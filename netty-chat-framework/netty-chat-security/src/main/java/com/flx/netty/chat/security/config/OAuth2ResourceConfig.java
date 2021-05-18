package com.flx.netty.chat.security.config;

import com.flx.netty.chat.common.utils.StringUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.security.handler.PermissionDeniedHandler;
import com.flx.netty.chat.security.handler.AuthenticationDeniedHandler;
import com.flx.netty.chat.security.interceptor.CustomPermissionInterceptor;
import com.flx.netty.chat.security.property.SecurityResourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
@Order(2)
@Configuration
@EnableResourceServer //开启了Resource Server功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启了方法级别的保护
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    /**
     * 默认服务资源ID
     */
    private final static String DEFAULT_RESOURCE_ID = "flx-oauth2-resource";

    @Resource(name = "resourceAuthenticationDeniedHandler")//自定义授权认证异常处理
    private AuthenticationDeniedHandler authenticationDeniedHandler;
    @Resource(name = "resourcePermissionDeniedHandler")//自定义权限异常处理
    private PermissionDeniedHandler permissionDeniedHandler;
    @Autowired//权限控制的配置属性
    private SecurityResourceProperties securityProperties;
//    @Autowired
//    private AccessDecisionManager accessDecisionManager;
    @Autowired//自定义权限过滤器
    private CustomPermissionInterceptor permissionInterceptor;

    /**
     * 设置token存储，这一点配置要与授权服务器相一致
     * 这里每次校验token不会去认证中心，而是直接去redis中校验
     * 校验token的方法为：
     * org.springframework.security.oauth2.provider.token.DefaultTokenServices#loadAuthentication(java.lang.String)
     */
    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore(){
        log.info("[Resource] Token store in redis !");
        LettuceConnectionFactory redisFactory = new LettuceConnectionFactory();//采用redis其他库
        redisFactory.setDatabase(securityProperties.getRedisIndex());//设置存储token的redis库
        redisFactory.afterPropertiesSet();
        return new RedisTokenStore(redisFactory);
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
        resources.authenticationEntryPoint(authenticationDeniedHandler);//自定义授权认证异常处理
        resources.accessDeniedHandler(permissionDeniedHandler);//自定义权限异常处理
    }

    /**
     * 配置资源访问规则
     * 由于ResourceServer优先级比较高这里会拦截一切请求导致WebSecurity的配置失效
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //所有资源可以访问
        if(securityProperties.isPermitAll()){
            http.authorizeRequests().anyRequest().permitAll();
        }else {
            List<String> authList = securityProperties.getAuthUrls();
            List<String> passList = securityProperties.getPassUrls();
            String[] authUrls = list2Array(authList);
            String[] passUrls = list2Array(passList);
            log.info("========>[Resource] authUrls = {}", JsonUtils.toJsonMsg(authUrls));
            log.info("========>[Resource] passUrls = {}", JsonUtils.toJsonMsg(passUrls));
            http
                //设置一个拒绝访问的提示
                .csrf().disable()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                    .requestMatchers()
                        .antMatchers(authUrls)
                 .and()
                    .authorizeRequests()//需要授权的访问地址
                        .antMatchers(passUrls).permitAll()//免授权访问URL
                        .anyRequest().authenticated();//剩下的需要授权访问
                        //.accessDecisionManager(accessDecisionManager);//授权访问的决策器
            //自定义权限过滤器在FilterSecurityInterceptor之后执行
            //FilterSecurityInterceptor访问http请求的核心过滤器
            http.addFilterAfter(permissionInterceptor, FilterSecurityInterceptor.class);
        }
    }

}
