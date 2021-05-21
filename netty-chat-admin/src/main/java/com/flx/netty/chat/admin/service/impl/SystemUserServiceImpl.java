package com.flx.netty.chat.admin.service.impl;

import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemUserVO;
import com.flx.netty.chat.admin.entity.SystemUser;
import com.flx.netty.chat.admin.dao.SystemUserDao;
import com.flx.netty.chat.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.enums.State;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;

import java.util.List;
import java.util.Map;
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
        Page<SystemUser> page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        return PageConvert.pageConvert(page,SystemUserVO.class);
    }

   public List<SystemUserVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemUserVO.class)).collect(Collectors.toList());
   }

   public List<SystemUserVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemUserVO.class)).collect(Collectors.toList());
   }

}
