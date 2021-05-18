package com.flx.netty.chat.security.interceptor;

import com.flx.netty.chat.security.permiss.CustomSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 14:31
 * @Description:
 */
@Slf4j
public class CustomPermissionInterceptor extends AbstractSecurityInterceptor implements Filter{

    private CustomSecurityMetadataSource securityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>>>>>>>CustomPermissionInterceptor init<<<<<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("CustomPermissionInterceptor doFilter");
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        invoke(filterInvocation);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // filterInvocation里面有一个被拦截的url
        // 里面调用 Oauth2AccessDecisionManager 的 getAttributes(Object object) 这个方法获取 filterInvocation 对应的所有权限
        // 再调用 Oauth2AccessDecisionManager 的 decide方法来校验用户的权限是否足够
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try {
            // 执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * 资源源数据定义
     * 获取请求所需要的权限资源
     * 正常如果部署在认证服务器上面可以直接数据库查询请求所需要的权限信息
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public void setSecurityMetadataSource(CustomSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

}
