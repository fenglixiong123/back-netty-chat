package com.flx.netty.chat.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flx.netty.chat.admin.dao.SystemPermissionDao;
import com.flx.netty.chat.admin.entity.SystemPermission;
import com.flx.netty.chat.admin.service.SystemPermissionService;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionDao, SystemPermission> implements SystemPermissionService {

    @Autowired
    private SystemPermissionDao permissionDao;

    private void codeTransform(List<SystemPermissionVO> voList){
//        if(voList==null)return;
//        voList.forEach(e->{
//            if(e.getState()!=null){
//                e.setStateDisplay(State.valueOf(State.class,e.getState()).getDesc());
//            }
//        });
    }

    @Override
    public boolean add(SystemPermissionVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, SystemPermission.class));
    }

    @Override
    public boolean delete(Long id){
        return super.removeById(id);
    }

    @Override
    public boolean update(SystemPermissionVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, SystemPermission.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                SystemPermission entity = new SystemPermission();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }

    @Override
    public SystemPermissionVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),SystemPermissionVO.class);
    }

    @Override
    public PageVO<SystemPermissionVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<SystemPermission> page;
        if(CollectionUtils.isEmpty(pageQuery.getQuery())){
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        }else {
            Map<String, Object> query = pageQuery.getQuery();
            QueryWrapper<SystemPermission> queryWrapper = new QueryWrapper<>();
            if(query.get(SystemPermission.ID)!=null) {
                queryWrapper.eq(SystemPermission.ID, query.get(SystemPermission.ID));
            }
            if(query.get(SystemPermission.PID)!=null) {
                queryWrapper.eq(SystemPermission.PID, query.get(SystemPermission.PID));
            }
            if(query.get(SystemPermission.CODE)!=null) {
                queryWrapper.like(SystemPermission.CODE, query.get(SystemPermission.CODE));
            }
            if(query.get(SystemPermission.NAME)!=null){
                queryWrapper.like(SystemPermission.NAME, query.get(SystemPermission.NAME));
            }
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        }
        PageVO<SystemPermissionVO> pageVO = PageConvert.pageConvert(page, SystemPermissionVO.class);
        codeTransform(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<SystemPermissionVO> query(Map<String,Object> columnMap) throws Exception{
        return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemPermissionVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SystemPermissionVO> queryAll() throws Exception{
        return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemPermissionVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SystemPermissionVO> getByIds(Set<Long> ids) throws Exception {
        return  permissionDao.getByIds(ids,State.effective.name())
                    .parallelStream().map(e -> BeanUtils.copyProperties(e, SystemPermissionVO.class)).collect(Collectors.toList());
    }

}
