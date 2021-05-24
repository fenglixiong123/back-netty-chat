package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.AuthPermission;
import com.flx.netty.chat.auth.crud.dao.AuthPermissionDao;
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
public class AuthPermissionManager extends BaseManager<AuthPermission, AuthPermissionDao> {

    public AuthPermission get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<AuthPermission> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<AuthPermission> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<AuthPermission> query(Object query) throws Exception {
        return super.query(query); 
    }
    
    public List<AuthPermission> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<AuthPermission> querySome(Object query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthPermission> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthPermission> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<AuthPermission> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(AuthPermission entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<AuthPermission> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(AuthPermission entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(AuthPermission entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(AuthPermission entity, String keyCode, String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(AuthPermission entity, String keyCode, String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(AuthPermission entity) throws Exception {
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

    public List<AuthPermission> getByIds(Set<Long> ids, String state) throws Exception {
        return this.dao.getByIds(ids,state);
    }

}
