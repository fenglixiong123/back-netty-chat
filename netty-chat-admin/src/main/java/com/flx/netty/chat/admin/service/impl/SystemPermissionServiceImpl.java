package com.flx.netty.chat.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flx.netty.chat.admin.dao.SystemPermissionDao;
import com.flx.netty.chat.admin.entity.SystemPermission;
import com.flx.netty.chat.admin.service.SystemPermissionService;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
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
        Page<SystemPermission> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,SystemPermissionVO.class);
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
