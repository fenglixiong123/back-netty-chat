package com.flx.netty.chat.user.crud.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.user.crud.dao.WebFriendDao;
import com.flx.netty.chat.user.crud.entity.WebFriend;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:24
 * @Description: 基础增删改查功能
 */
@Service
public class FriendManager extends BaseManager<WebFriend, WebFriendDao> {

    public WebFriend get(Long id) throws Exception {
        return (WebFriend)super.get(id);
    }

    public IPage<WebFriend> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }


    public IPage<WebFriend> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }

    public List<WebFriend> query(Object query) throws Exception {
        return super.query(query);
    }

    public List<WebFriend> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }

    public List<WebFriend> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebFriend> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebFriend> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }

    public List<WebFriend> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }

    public Long add(WebFriend entity) throws Exception {
        return super.add(entity);
    }

    public Integer add(List<WebFriend> entityList) throws Exception {
        return super.add(entityList);
    }

    public Integer update(WebFriend entity) throws Exception {
        return super.update(entity);
    }

    public Integer updateNull(WebFriend entity) throws Exception {
        return super.updateNull(entity);
    }

    public Integer update(WebFriend entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }

    public Integer updateNull(WebFriend entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }

    public Integer updateState(WebFriend entity) throws Exception {
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
