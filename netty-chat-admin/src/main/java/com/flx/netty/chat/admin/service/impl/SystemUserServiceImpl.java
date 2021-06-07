package com.flx.netty.chat.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flx.netty.chat.admin.dao.SystemPermissionDao;
import com.flx.netty.chat.admin.dao.SystemUserRoleDao;
import com.flx.netty.chat.admin.entity.SystemUserRole;
import com.flx.netty.chat.admin.service.SystemPermissionService;
import com.flx.netty.chat.admin.service.SystemRoleService;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.admin.vo.SystemUserVO;
import com.flx.netty.chat.admin.entity.SystemUser;
import com.flx.netty.chat.admin.dao.SystemUserDao;
import com.flx.netty.chat.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
import com.flx.netty.chat.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-21
 */
@Slf4j
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserDao, SystemUser> implements SystemUserService {

    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemUserRoleDao userRoleDao;

    private void codeTransform(List<SystemUserVO> voList){
//        if(voList==null)return;
//        voList.forEach(e->{
//            if(e.getState()!=null){
//                e.setStateDisplay(State.valueOf(State.class,e.getState()).getDesc());
//            }
//        });
    }

    public boolean add(SystemUserVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, SystemUser.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(SystemUserVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, SystemUser.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                SystemUser entity = new SystemUser();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public SystemUserVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),SystemUserVO.class);
    }
    
    public PageVO<SystemUserVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<SystemUser> page;
        if(CollectionUtils.isEmpty(pageQuery.getQuery())){
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        }else {
            Map<String, Object> query = pageQuery.getQuery();
            QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
            if(query.get(SystemUser.ID)!=null) {
                queryWrapper.eq(SystemUser.ID, query.get(SystemUser.ID));
            }
            if(query.get(SystemUser.USER_NAME)!=null) {
                queryWrapper.eq(SystemUser.USER_NAME, query.get(SystemUser.USER_NAME));
            }
            if(query.get(SystemUser.NICK_NAME)!=null) {
                queryWrapper.like(SystemUser.NICK_NAME, query.get(SystemUser.NICK_NAME));
            }
            if(query.get(SystemUser.PHONE)!=null){
                queryWrapper.eq(SystemUser.PHONE, query.get(SystemUser.PHONE));
            }
            if(query.get(SystemUser.SEX)!=null) {
                queryWrapper.like(SystemUser.SEX, query.get(SystemUser.SEX));
            }
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        }
        PageVO<SystemUserVO> pageVO = PageConvert.pageConvert(page, SystemUserVO.class);
        codeTransform(pageVO.getRecords());
        return pageVO;
    }

    public List<SystemUserVO> query(Map<String,Object> columnMap) throws Exception{
        return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemUserVO.class)).collect(Collectors.toList());
    }

    public List<SystemUserVO> queryAll() throws Exception{
        return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemUserVO.class)).collect(Collectors.toList());
    }

    public SystemUserVO getByUsername(String username) throws Exception{
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SystemUser.USER_NAME,username);
        return BeanUtils.copyProperties(super.getOne(queryWrapper),SystemUserVO.class);
    }

    @Override
    public List<SystemPermissionVO> getPermissionById(Long id) throws Exception {
        List<SystemUserRole> userRoles = userRoleDao.getByUserId(id, State.effective.name());
        Set<Long> roleIds = userRoles.parallelStream().map(SystemUserRole::getRoleId).collect(Collectors.toSet());
        return roleService.getPermissionByIds(roleIds);
    }

    @Override
    public List<SystemMenuVO> getMenuById(Long id) throws Exception {
        List<SystemUserRole> userRoles = userRoleDao.getByUserId(id, State.effective.name());
        Set<Long> roleIds = userRoles.parallelStream().map(SystemUserRole::getRoleId).collect(Collectors.toSet());
        return roleService.getMenuByIds(roleIds);
    }

}
