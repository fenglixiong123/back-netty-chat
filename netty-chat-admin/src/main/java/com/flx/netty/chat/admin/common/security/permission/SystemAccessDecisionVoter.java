package com.flx.netty.chat.admin.common.security.permission;

import com.flx.netty.chat.admin.common.security.property.SecurityProperties;
import com.flx.netty.chat.admin.common.security.user.SystemUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/17 14:31
 * @Description: 权限决策
 */
@Slf4j
public class SystemAccessDecisionVoter implements AccessDecisionVoter<Object> {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 判定是否拥有权限的决策方法
     * @param authentication 用户认证信息
     * @param filterInvocation 包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest()
     * @param attributes 此方法是为了判定用户请求的url是否在权限表中，如果在权限表中，则返回给decide方法，用来判定用户是否有此权限。如果在权限表中则放行。
     */
    @Override
    public int vote(Authentication authentication, Object filterInvocation, Collection<ConfigAttribute> attributes) {

        if(authentication==null){
            log.error("AccessVoter failure,reason : authentication is null !");
            return ACCESS_DENIED;
        }
        SystemUserDetails user = (SystemUserDetails)authentication.getPrincipal();
        if(user==null||user.getUsername()==null){
            log.info("AccessVoter failure,reason : user is null !");
            return ACCESS_DENIED;
        }
        if(user.getUsername().equals("super")){
            log.info("AccessVoter successful,reason : super user !");
            return ACCESS_GRANTED;
        }
        HttpServletRequest request = ((FilterInvocation) filterInvocation).getHttpRequest();
        String url = request.getRequestURI();
        if(isWhitePermit(url)){
            log.info("AccessVoter successful,reason : white url");
            return ACCESS_GRANTED;
        }
        if(user.getPowers()==null){
            log.info("AccessVoter failure,reason : permission is null !");
            return ACCESS_DENIED;
        }
        for (String path:user.getPowers()){
            if(pathMatcher.match(path,url)){
                log.info("AccessVoter successful,reason : has right !");
                return ACCESS_GRANTED;
            }
        }
        log.info("AccessVoter failure,reason : no right !");
        return ACCESS_DENIED;

    }

    private boolean isWhitePermit(String url){
        List<String> passUrls = securityProperties.getWhitePermits();
        if(CollectionUtils.isEmpty(passUrls)){
            return false;
        }
        for (String passUrl : passUrls){
            String[] arrays = passUrl.split(",");
            for (String pass : arrays){
                if(pathMatcher.match(pass,url)){
                    return true;
                }
            }
        }
        return false;
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