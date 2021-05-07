package com.flx.netty.chat.auth.crud.manager;

import com.flx.netty.chat.auth.crud.entity.ClientDetail;
import com.flx.netty.chat.auth.crud.dao.ClientDetailDao;
import org.springframework.stereotype.Service;
import com.flx.netty.chat.plugin.plugins.mybatis.base.BaseManager;
import com.flx.netty.chat.common.enums.State;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 * 客户端信息表 Manager层操作类
 *
 * @author Fenglixiong
 * @since 2021-05-07
 */
@Slf4j
@Service
public class ClientDetailManager extends BaseManager<ClientDetail, ClientDetailDao> {

    public ClientDetail get(Long id) throws Exception {
        return super.get(id);
    }
    
    public IPage<ClientDetail> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        return super.queryPage(pageNum, pageSize, query);
    }
    
    
    public IPage<ClientDetail> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        return super.queryPage(pageNum, pageSize, query); 
    }
    
    public List<ClientDetail> query(Object query) throws Exception { 
        return super.query(query); 
    }
    
    public List<ClientDetail> query(Map<String, Object> query) throws Exception {
        return super.query(query);
    }
    
    public List<ClientDetail> querySome(Object query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<ClientDetail> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return super.querySome(query,columns);
    }
    
    public List<ClientDetail> queryAll(Object query) throws Exception {
        return super.queryAll(query);
    }
    
    public List<ClientDetail> queryAll(Map<String, Object> query) throws Exception {
        return super.queryAll(query);
    }
    
    public Long add(ClientDetail entity) throws Exception {
        return super.add(entity);
    }
    
    public Integer add(List<ClientDetail> entityList) throws Exception {
        return super.add(entityList);
    }
    
    public Integer update(ClientDetail entity) throws Exception {
        return super.update(entity);
    }
    
    public Integer updateNull(ClientDetail entity) throws Exception {
        return super.updateNull(entity);
    }
    
    public Integer update(ClientDetail entity,String keyCode,String keyValue) throws Exception {
        return super.update(entity,keyCode,keyValue);
    }
    
    public Integer updateNull(ClientDetail entity,String keyCode,String keyValue) throws Exception {
        return super.updateNull(entity,keyCode,keyValue);
    }
    
    public Integer updateState(ClientDetail entity) throws Exception {
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
