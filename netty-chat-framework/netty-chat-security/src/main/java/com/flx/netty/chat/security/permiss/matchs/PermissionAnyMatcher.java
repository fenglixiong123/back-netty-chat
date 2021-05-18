package com.flx.netty.chat.security.permiss.matchs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 16:38
 * @Description: 只要有一个匹配则返回true
 */
public class PermissionAnyMatcher implements PermissionMatcher {

    /**
     * @param attributes 请求所需要的权限
     * @param authorities 用户拥有的权限
     * @return
     */
    @Override
    public boolean match(Collection<ConfigAttribute> attributes, Collection<? extends GrantedAuthority> authorities) {
        if(attributes.isEmpty()){
            return true;
        }
        if(authorities.isEmpty()){
            return false;
        }
        for (ConfigAttribute attribute : attributes) {
            for (GrantedAuthority authority : authorities) {
                String authCode = authority.getAuthority();
                if(StringUtils.isNotBlank(authCode) && authCode.equals(attribute.getAttribute())){
                    return true;
                }
            }
        }
        return false;
    }

}
