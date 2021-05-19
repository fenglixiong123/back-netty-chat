package com.flx.netty.chat.admin.common.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fenglixiong
 * @Create 2021/5/20 2:48
 * @Description
 **/
@Slf4j
@Configuration
public class AppBeanConfiguration {

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //设置ip白名单
        //servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //设置ip黑名单，优先级高于白名单
        //servletRegistrationBean.addInitParameter("deny", "192.168.0.19");
        //设置控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "welcome");
        //是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        //创建过滤器
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
