package com.flx.netty.chat.auth.crud.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.auth.crud.dao.WebUserDao;
import com.flx.netty.chat.auth.crud.entity.WebUser;
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
public class UserManager extends BaseManager<WebUser, WebUserDao> {

    public WebUser get(Long id) throws Exception {
        return super.get(id);
    }

    public IPage<WebUser> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }


    public IPage<WebUser> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }

    public List<WebUser> query(Object query) throws Exception {
        return super.query(query);
    }

    public List<WebUser> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }

    public List<WebUser> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebUser> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebUser> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }

    public List<WebUser> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }

    public Long add(WebUser entity) throws Exception {
        return super.add(entity);
    }

    public Integer add(List<WebUser> entityList) throws Exception {
        return super.add(entityList);
    }

    public Integer update(WebUser entity) throws Exception {
        return super.update(entity);
    }

    public Integer updateNull(WebUser entity) throws Exception {
        return super.updateNull(entity);
    }

    public Integer update(WebUser entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }

    public Integer updateNull(WebUser entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }

    public Integer updateState(WebUser entity) throws Exception {
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
