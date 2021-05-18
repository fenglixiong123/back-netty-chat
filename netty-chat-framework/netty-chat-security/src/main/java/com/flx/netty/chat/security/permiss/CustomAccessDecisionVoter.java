package com.flx.netty.chat.security.permiss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/17 14:31
 * @Description: 权限决策
 */
@Slf4j
public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 判定是否拥有权限的决策方法
     * @param authentication 用户认证信息
     * @param filterInvocation 包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest()
     * @param attributes 此方法是为了判定用户请求的url是否在权限表中，如果在权限表中，则返回给decide方法，用来判定用户是否有此权限。如果在权限表中则放行。
     */
    @Override
    public int vote(Authentication authentication, Object filterInvocation, Collection<ConfigAttribute> attributes) {
        log.info("开始权限投票中...");
        if(authentication==null){
            log.error("AccessVoter authentication is null !");
            return ACCESS_DENIED;
        }
        if (Objects.isNull(filterInvocation)) {
            log.error("AccessVoter filterInvocation is null !");
            return ACCESS_DENIED;
        }
        HttpServletRequest request = ((FilterInvocation) filterInvocation).getHttpRequest();
        String url = request.getRequestURI();
        String method = request.getMethod();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.isEmpty()) {
            log.error("AccessVoter authorities is null !");
            return ACCESS_DENIED;
        }
        for (GrantedAuthority authority:authorities){
            String authCode = authority.getAuthority();
            if(StringUtils.isBlank(authCode)){
                return ACCESS_DENIED;
            }
            int num = authCode.indexOf("#");
            if(method.equals(authCode.substring(0,num+1)) && pathMatcher.match(authCode.substring(num),url)){
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;

    }


    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class aClass) {
        return true;
    }
}

/*



 */