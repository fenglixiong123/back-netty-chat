package com.flx.netty.chat.auth.crud.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.auth.crud.dao.AuthUserDao;
import com.flx.netty.chat.auth.crud.entity.AuthUser;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:24
 * @Description: 基础增删改查功能
 */
@Service
public class AuthUserManager extends BaseManager<AuthUser, AuthUserDao> {

    public AuthUser get(Long id) throws Exception {
        return super.get(id);
    }

    public IPage<AuthUser> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }


    public IPage<AuthUser> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }

    public List<AuthUser> query(Object query) throws Exception {
        return super.query(query);
    }

    public List<AuthUser> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }

    public List<AuthUser> querySome(Object query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<AuthUser> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<AuthUser> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }

    public List<AuthUser> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }

    public Long add(AuthUser entity) throws Exception {
        return super.add(entity);
    }

    public Integer add(List<AuthUser> entityList) throws Exception {
        return super.add(entityList);
    }

    public Integer update(AuthUser entity) throws Exception {
        return super.update(entity);
    }

    public Integer updateNull(AuthUser entity) throws Exception {
        return super.updateNull(entity);
    }

    public Integer update(AuthUser entity, String keyCode, String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }

    public Integer updateNull(AuthUser entity, String keyCode, String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }

    public Integer updateState(AuthUser entity) throws Exception {
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
