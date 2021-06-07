package com.flx.netty.chat.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flx.netty.chat.admin.entity.SystemRole;
import com.flx.netty.chat.admin.utils.PageConvert;
import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.admin.entity.SystemMenu;
import com.flx.netty.chat.admin.dao.SystemMenuDao;
import com.flx.netty.chat.admin.service.SystemMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flx.netty.chat.admin.vo.SystemRoleVO;
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单信息 服务实现类
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
@Slf4j
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuDao, SystemMenu> implements SystemMenuService {

    @Autowired
    private SystemMenuDao menuDao;

    private void codeTransform(List<SystemMenuVO> voList){
//        if(voList==null)return;
//        voList.forEach(e->{
//            if(e.getState()!=null){
//                e.setStateDisplay(State.valueOf(State.class,e.getState()).getDesc());
//            }
//        });
    }

    public boolean add(SystemMenuVO entityVO) throws Exception{
        return super.save(BeanUtils.copyProperties(entityVO, SystemMenu.class));
    }
    
    public boolean delete(Long id){
        return super.removeById(id);
    }
    
    public boolean update(SystemMenuVO entityVO) throws Exception{
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空！");
        }
        return super.updateById(BeanUtils.copyProperties(entityVO, SystemMenu.class));
    }
    
    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id:entityVO.getIds()){
            if(entityVO.getState().equals(State.deleted.name())){
                super.removeById(id);
            }else {
                SystemMenu entity = new SystemMenu();
                entity.setId(id);
                entity.setState(entity.getState());
                entity.setUpdateUser(entityVO.getUpdateUser());
                super.updateById(entity);
            }
        }
        return true;
    }
    
    public SystemMenuVO get(Long id) throws Exception{
        return BeanUtils.copyProperties(super.getById(id),SystemMenuVO.class);
    }
    
    public PageVO<SystemMenuVO> queryPage(PageQuery pageQuery) throws Exception{
        Page<SystemMenu> page;
        if(CollectionUtils.isEmpty(pageQuery.getQuery())){
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()));
        }else {
            Map<String, Object> query = pageQuery.getQuery();
            QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
            if(query.get(SystemMenu.ID)!=null) {
                queryWrapper.eq(SystemMenu.ID, query.get(SystemMenu.ID));
            }
            if(query.get(SystemMenu.PID)!=null) {
                queryWrapper.eq(SystemMenu.PID, query.get(SystemMenu.PID));
            }
            if(query.get(SystemMenu.CODE)!=null) {
                queryWrapper.like(SystemMenu.CODE, query.get(SystemMenu.CODE));
            }
            if(query.get(SystemRole.NAME)!=null){
                queryWrapper.like(SystemMenu.NAME, query.get(SystemMenu.NAME));
            }
            page = super.page(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        }
        PageVO<SystemMenuVO> pageVO = PageConvert.pageConvert(page, SystemMenuVO.class);
        codeTransform(pageVO.getRecords());
        return pageVO;
    }

   public List<SystemMenuVO> query(Map<String,Object> columnMap) throws Exception{
       return super.listByMap(columnMap).parallelStream().map(e->BeanUtils.copyProperties(e,SystemMenuVO.class)).collect(Collectors.toList());
   }

   public List<SystemMenuVO> queryAll() throws Exception{
       return super.list().parallelStream().map(e->BeanUtils.copyProperties(e,SystemMenuVO.class)).collect(Collectors.toList());
   }

    @Override
    public List<SystemMenuVO> getByIds(Set<Long> ids) throws Exception {
        return menuDao.getByIds(ids,State.effective.name()).parallelStream().map(e->BeanUtils.copyProperties(e,SystemMenuVO.class)).collect(Collectors.toList());
    }

}
