package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.AuthRolePermission;
import com.flx.netty.chat.auth.crud.dao.AuthRolePermissionDao;
import org.springframework.stereotype.Service;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.common.enums.State;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Manager层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Slf4j
@Service
public class AuthRolePermissionManager extends BaseManager<AuthRolePermission, AuthRolePermissionDao> {

    public AuthRolePermission get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<AuthRolePermission> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<AuthRolePermission> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<AuthRolePermission> query(Object query) throws Exception {
        return super.query(query); 
    }
    
    public List<AuthRolePermission> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<AuthRolePermission> querySome(Object query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthRolePermission> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthRolePermission> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<AuthRolePermission> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(AuthRolePermission entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<AuthRolePermission> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(AuthRolePermission entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(AuthRolePermission entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(AuthRolePermission entity, String keyCode, String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(AuthRolePermission entity, String keyCode, String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(AuthRolePermission entity) throws Exception {
        if(entity.getState().equals(State.deleted.name())){
            return super.delete(entity.getId());
        }
        return super.update(entity);
    }

    public Integer delete(Long id) throws Exception {
        return super.delete(id);
    }

    public boolean isExist(String keyCode,String keyValue) throws Exception {
        return super.isExist(keyCode,keyValue);
    }

    public boolean isExist(Long id) throws Exception {
        return super.isExist(id);
    }

    /**
     * 通过角色id获取角色权限关系
     * @param roleId 角色id
     * @param state 数据状态
     */
    public List<AuthRolePermission> getByRoleId(Long roleId, String state){
        return this.dao.getByRoleId(roleId,state);
    }

    /**
     * 通过角色id获取角色权限关系
     * @param roleIds 角色id
     * @param state 数据状态
     */
    public List<AuthRolePermission> getByRoleIds(Set<Long> roleIds, String state) throws Exception {
        return this.dao.getByRoleIds(roleIds,state);
    }
     
}
