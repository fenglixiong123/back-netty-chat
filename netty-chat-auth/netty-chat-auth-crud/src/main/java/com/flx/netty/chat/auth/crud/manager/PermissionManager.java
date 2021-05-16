package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.WebPermission;
import com.flx.netty.chat.auth.crud.dao.PermissionDao;
import org.apache.ibatis.annotations.Param;
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
public class PermissionManager extends BaseManager<WebPermission, PermissionDao> {

    public WebPermission get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<WebPermission> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<WebPermission> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<WebPermission> query(Object query) throws Exception {
        return super.query(query); 
    }
    
    public List<WebPermission> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<WebPermission> querySome(Object query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<WebPermission> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<WebPermission> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<WebPermission> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(WebPermission entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<WebPermission> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(WebPermission entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(WebPermission entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(WebPermission entity, String keyCode, String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(WebPermission entity, String keyCode, String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(WebPermission entity) throws Exception {
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

    public List<WebPermission> getByIds(Set<Long> ids, String state) throws Exception {
        return this.dao.getByIds(ids,state);
    }

}
