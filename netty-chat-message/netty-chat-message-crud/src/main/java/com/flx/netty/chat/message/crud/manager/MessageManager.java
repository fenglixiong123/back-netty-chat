package com.flx.netty.chat.message.crud.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.message.crud.dao.WebMessageDao;
import com.flx.netty.chat.message.crud.entity.WebMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 17:24
 * @Description: 基础增删改查功能
 */
@Service
public class MessageManager extends BaseManager<WebMessage, WebMessageDao> {

    public WebMessage get(Long id) throws Exception {
        return (WebMessage)super.get(id);
    }

    public IPage<WebMessage> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }


    public IPage<WebMessage> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }

    public List<WebMessage> query(Object query) throws Exception {
        return super.query(query);
    }

    public List<WebMessage> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }

    public List<WebMessage> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebMessage> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }

    public List<WebMessage> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }

    public List<WebMessage> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }

    public Long add(WebMessage entity) throws Exception {
        return super.add(entity);
    }

    public Integer add(List<WebMessage> entityList) throws Exception {
        return super.add(entityList);
    }

    public Integer update(WebMessage entity) throws Exception {
        return super.update(entity);
    }

    public Integer updateNull(WebMessage entity) throws Exception {
        return super.updateNull(entity);
    }

    public Integer update(WebMessage entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }

    public Integer updateNull(WebMessage entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }

    public Integer updateState(WebMessage entity) throws Exception {
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
