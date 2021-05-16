package com.flx.netty.chat.auth.console.service.impl;

import com.flx.netty.chat.auth.crud.entity.UserRole;
import com.flx.netty.chat.auth.crud.manager.UserRoleManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Service服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-16
 */
@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleManager userRoleManager;

    private void convertVO(List<UserRoleVO> entityList){
   
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(UserRoleVO entityVO) throws Exception {
        UserRole entity = userRoleManager.get(entityVO.getId());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return userRoleManager.add(BeanUtils.copyProperties(entityVO, UserRole.class));
    }
   
    @Override
    public Integer delete(Long id) throws Exception {
        return userRoleManager.delete(id);
    }
   
    @Override
    public Integer update(UserRoleVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return userRoleManager.update(BeanUtils.copyProperties(entityVO, UserRole.class));
    }
   
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            UserRole entity = new UserRole();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            userRoleManager.updateState(entity);
        }
        return true;
    }
   
    @Override
    public UserRoleVO get(Long id) throws Exception {
        UserRole entity = userRoleManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, UserRoleVO.class);
    }
   
    @Override
    public PageVO<UserRoleVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<UserRole> iPage = userRoleManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<UserRoleVO> pageVO = PageConvert.pageConvert(iPage, UserRoleVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }
   
    @Override
    public List<UserRoleVO> query(Map<String, Object> query) throws Exception {
        return userRoleManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, UserRoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<UserRoleVO> querySome(Map<String, Object> query,String[] columns) throws Exception {
        return userRoleManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, UserRoleVO.class)).collect(Collectors.toList());
    }
   
    @Override
    public List<UserRoleVO> queryAll(Map<String, Object> query) throws Exception {
        return userRoleManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, UserRoleVO.class)).collect(Collectors.toList());
    }
 
}
