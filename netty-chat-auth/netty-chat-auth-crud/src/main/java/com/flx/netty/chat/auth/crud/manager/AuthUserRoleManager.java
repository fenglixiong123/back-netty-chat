package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.AuthUserRole;
import com.flx.netty.chat.auth.crud.dao.AuthUserRoleDao;
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
public class AuthUserRoleManager extends BaseManager<AuthUserRole, AuthUserRoleDao> {

    public AuthUserRole get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<AuthUserRole> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<AuthUserRole> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<AuthUserRole> query(Object query) throws Exception {
        return super.query(query); 
    }
    
    public List<AuthUserRole> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<AuthUserRole> querySome(Object query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthUserRole> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<AuthUserRole> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<AuthUserRole> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(AuthUserRole entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<AuthUserRole> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(AuthUserRole entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(AuthUserRole entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(AuthUserRole entity, String keyCode, String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(AuthUserRole entity, String keyCode, String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(AuthUserRole entity) throws Exception {
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
     * 通过用户id获取用户角色关系
     * @param userId 用户id
     * @param state 数据状态
     */
    public List<AuthUserRole> getByUserId(Long userId, String state) throws Exception {
        return this.dao.getByUserId(userId,state);
    }
     
}
