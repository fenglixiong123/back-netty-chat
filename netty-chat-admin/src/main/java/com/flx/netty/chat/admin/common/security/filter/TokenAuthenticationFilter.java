package com.flx.netty.chat.admin.common.security.filter;

import com.flx.netty.chat.admin.common.security.auth.AuthManager;
import com.flx.netty.chat.admin.common.security.user.SystemUserDetails;
import com.flx.netty.chat.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.flx.netty.chat.admin.common.security.constants.SecurityConstant.HEADER_TOKEN_KEY;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/26 11:36
 * @Description:
 * 客户端登录成功时，后台会把生成的 token 返回给前端，之后客户端每次请求后台接口将会把这个 token 附在 header 头中传递给后台，
 * 后台会验证这个 token 是否有效，如果有效就把用户信息加载至 SpringSecurity 中，如果无效则会跳转至上一步提供 AuthenticationEntryPoint 进行处理。
 */
@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthManager authManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HEADER_TOKEN_KEY);
        if(StringUtils.isNotBlank(token)){
            try {
                SystemUserDetails user = authManager.getUserInfo(token);
                user.setToken(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated user %s, setting security context", user.getUsername()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                authManager.refreshToken(token);//刷新token有效期
            } catch (Exception e) {
                log.error("Token filter error : {}",e.getMessage());
            }

        }
        filterChain.doFilter(request,response);
    }

}
