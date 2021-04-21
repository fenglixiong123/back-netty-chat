package com.flx.netty.chat.group.crud.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.group.crud.dao.WebGroupUserDao;
import com.flx.netty.chat.group.crud.entity.WebGroupUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:24
 * @Description: 基础增删改查功能
 */
@Service
public class GroupUserManager extends BaseManager<WebGroupUser, WebGroupUserDao> {

    public WebGroupUser get(Long id) throws Exception {
        return (WebGroupUser)super.get(id);
    }

    public IPage<WebGroupUser> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }


    public IPage<WebGroupUser> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }

    public List<WebGroupUser> query(Object query) throws Exception {
        return super.query(query);
    }

    public List<WebGroupUser> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }

    public List<WebGroupUser> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebGroupUser> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebGroupUser> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }

    public List<WebGroupUser> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }

    public Long add(WebGroupUser entity) throws Exception {
        return super.add(entity);
    }

    public Integer add(List<WebGroupUser> entityList) throws Exception {
        return super.add(entityList);
    }

    public Integer update(WebGroupUser entity) throws Exception {
        return super.update(entity);
    }

    public Integer updateNull(WebGroupUser entity) throws Exception {
        return super.updateNull(entity);
    }

    public Integer update(WebGroupUser entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }

    public Integer updateNull(WebGroupUser entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }

    public Integer updateState(WebGroupUser entity) throws Exception {
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
