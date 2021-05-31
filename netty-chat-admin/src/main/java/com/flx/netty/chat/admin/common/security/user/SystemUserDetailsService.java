package com.flx.netty.chat.admin.common.security.user;

import com.flx.netty.chat.admin.dao.SystemUserDao;
import com.flx.netty.chat.admin.entity.SystemUser;
import com.flx.netty.chat.admin.service.SystemUserService;
import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.auth.api.enums.UserStateEnum;
import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.date.DateUtils;
import com.flx.netty.chat.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/2 17:55
 * @Description: 通过用户名查询用户数据
 *
 */
@Slf4j
@Component
public class SystemUserDetailsService implements UserDetailsService {

    @Autowired
    private SystemUserDao userDao;
    @Autowired
    private SystemUserService userService;

    /**
     * 根据用户名获取用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            //从数据库查询用户
            log.info("loadUserByUsername username = {}",username);
            SystemUser user = userDao.getByUsername(username);
            log.info("loadUserByUsername user = {}", JsonUtils.toJsonMsg(user));
            boolean enabled = user.getState().equals(UserStateEnum.effective.name());
            boolean expired = user.getState().equals(UserStateEnum.expired.name())|| (user.getExpireTime() != null && DateUtils.beforeNow(user.getExpireTime()));
            boolean locked = user.getState().equals(UserStateEnum.locked.name());
            return new SystemUserDetails()
                    .setUserId(user.getId())
                    .setUsername(user.getUserName())
                    .setPassword(user.getPassword())
                    .setEnabled(enabled).setAccountNonExpired(!expired)
                    .setAccountNonLocked(!locked)
                    .setCredentialsNonExpired(true)
                    .setMenus(getMenu(user.getId()))
                    .setPowers(getPower(user.getId()))
                    .setAuthorities(getAuthorities(user.getId()));
        } catch (Exception e) {
            log.error("loadUserByUsername error username = {},message = {} !",username,e.getMessage());
            throw new UsernameNotFoundException("User [" + username + "] not exist !");
        }

    }

    /**
     * 获取用户权限
     * @param id
     * @return
     * @throws Exception
     */
    private List<String> getPower(Long id)throws Exception{
        return userService.getPermissionById(id)
                .parallelStream()
                .map(SystemPermissionVO::getPath)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获取用户权限
     * @param id
     * @return
     * @throws Exception
     */
    private List<String> getMenu(Long id)throws Exception{
        return userService.getMenuById(id)
                .parallelStream()
                .map(SystemMenuVO::getPath)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获取用户权限集合
     */
    private Set<SystemAuthority> getAuthorities(Long userId) throws Exception {
        /*try {
            Function<SystemPermissionVO,SystemAuthority> authMapper = (e)-> {
                if(StringUtils.isNotBlank(e.getPath())){
                    return new SystemAuthority(e.getPath());
                }
                return null;
            };
            List<SystemPermissionVO> permissions = userService.getPermissionById(userId);
            Set<SystemAuthority> authorities = permissions.parallelStream().map(authMapper).filter(Objects::nonNull).collect(Collectors.toSet());
            if(CollectionUtils.isNotEmpty(authorities)){
                log.info("load authorities count = {}",authorities.size());
                return authorities;
            }
            log.error("load authorities null !");
            return new HashSet<>();
        } catch (Exception e) {
            throw new Exception("Get permission error : "+e.getMessage());
        }
         */
        return new HashSet<>();
    }

}
