package com.flx.netty.chat.auth.console.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.auth.api.vo.WebUserVO;
import com.flx.netty.chat.auth.crud.manager.UserManager;
import com.flx.netty.chat.auth.crud.entity.WebUser;
import com.flx.netty.chat.auth.console.service.UserService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;
import com.flx.netty.chat.plugin.plugins.mybatis.page.PageConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/24 16:34
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    private void convertVO(List<WebUserVO> entityList){

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(WebUserVO entityVO) throws Exception {
        WebUserVO entity = getByUsername(entityVO.getUserName());
        if(entity!=null){
            throw new Exception("不能重复添加!");
        }
        return userManager.add(BeanUtils.copyProperties(entityVO, WebUser.class));
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return userManager.delete(id);
    }

    @Override
    public Integer update(WebUserVO entityVO) throws Exception {
        if(entityVO.getId()==null){
            throw new Exception("Id不能为空!");
        }
        return userManager.update(BeanUtils.copyProperties(entityVO, WebUser.class));
    }

    @Override
    public boolean updateState(UpdateState entityVO) throws Exception {
        for (Long id : entityVO.getIds()){
            WebUser entity = new WebUser();
            entity.setId(id);
            entity.setState(entityVO.getState());
            entity.setUpdateUser(entityVO.getUpdateUser());
            userManager.updateState(entity);
        }
        return true;
    }

    @Override
    public WebUserVO get(Long id) throws Exception {
        WebUser entity = userManager.get(id);
        if(entity==null){
            return null;
        }
        return BeanUtils.copyProperties(entity, WebUserVO.class);
    }

    @Override
    public PageVO<WebUserVO> queryPage(PageQuery pageQuery) throws Exception {
        IPage<WebUser> iPage = userManager.queryPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getQuery());
        PageVO<WebUserVO> pageVO = PageConvert.pageConvert(iPage, WebUserVO.class);
        convertVO(pageVO.getRecords());
        return pageVO;
    }

    @Override
    public List<WebUserVO> query(Map<String, Object> query) throws Exception {
        return userManager.query(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebUserVO> querySome(Map<String, Object> query, String[] columns) throws Exception {
        return userManager.querySome(query,columns).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<WebUserVO> queryAll(Map<String, Object> query) throws Exception {
        return userManager.queryAll(query).parallelStream().map(e -> BeanUtils.copyProperties(e, WebUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public WebUserVO getByUsername(String username) throws Exception{
        return BeanUtils.copyProperties(userManager.get("userName", username), WebUserVO.class);
    }

    @Override
    public boolean isExist(String username) throws Exception{
        return userManager.isExist("userName",username);
    }

    @Override
    public WebUserVO validateUser(String username, String password) throws Exception {
        if(StringUtils.isBlank(username)){
            throw new Exception("用户名不能为空！");
        }
        if(StringUtils.isBlank(password)){
            throw new Exception("密码不能为空！");
        }
        WebUser user = userManager.get("userName", username);;
        if(user==null){
            throw new Exception("用户名不存在！");
        }
        if(!user.getPassword().equals(password)){
            throw new Exception("用户密码错误！");
        }
        return BeanUtils.copyProperties(user, WebUserVO.class);
    }


}
