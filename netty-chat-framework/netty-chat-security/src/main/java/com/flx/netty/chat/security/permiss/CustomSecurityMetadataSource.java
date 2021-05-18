package com.flx.netty.chat.security.permiss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/18 15:15
 * @Description:
 */
@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //@Autowired
    //private PermissionDao permissionDao;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public CustomSecurityMetadataSource(){
        //todo 从数据库加载权限配置
    }

    /**
     * 获取请求所需要的权限信息
     * @param object 可以转化为请求
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        Map<String,String> permissionMap = loadUrlPermissionMap();

        FilterInvocation fi = ((FilterInvocation) object);
        String url = fi.getRequestUrl();
        for(Map.Entry<String,String> entry:permissionMap.entrySet()) {
            String path = entry.getKey();
            if(pathMatcher.match(path,url)) {
                return SecurityConfig.createList(entry.getValue().split(","));
            }
        }

        return new ArrayList<>();
    }

    /**
     * 每个请求对应的权限
     * 这里需要从数据库加载,可以从缓存的redis加载(注意登录时候写入，注销时候注销)
     */
    private Map<String, String> loadUrlPermissionMap() {
        //开始从redis读取权限信息
        Map<String,String> permissionMap = new HashMap<>();
        permissionMap.put("/user/get/**","ROLE_ANONYMOUS,METHOD_GET");
        permissionMap.put("/user/add","ROLE_ANONYMOUS,METHOD_POST");
        permissionMap.put("/user/update","ROLE_ADMIN,METHOD_PUT");
        permissionMap.put("/user/delete/**","ROLE_USER,METHOD_DELETE");
        return permissionMap;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
