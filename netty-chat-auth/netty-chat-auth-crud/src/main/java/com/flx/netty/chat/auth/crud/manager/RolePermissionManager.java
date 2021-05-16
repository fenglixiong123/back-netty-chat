package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.RolePermission;
import com.flx.netty.chat.auth.crud.dao.RolePermissionDao;
import org.springframework.stereotype.Service;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.common.enums.State;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 *  Manager层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Slf4j
@Service
public class RolePermissionManager extends BaseManager<RolePermission, RolePermissionDao> {

    public RolePermission get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<RolePermission> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<RolePermission> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<RolePermission> query(Object query) throws Exception { 
        return super.query(query); 
    }
    
    public List<RolePermission> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<RolePermission> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<RolePermission> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<RolePermission> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<RolePermission> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(RolePermission entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<RolePermission> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(RolePermission entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(RolePermission entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(RolePermission entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(RolePermission entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(RolePermission entity) throws Exception {
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
     
}
