package com.flx.netty.chat.security.filter;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Fenglixiong
 * @Create 2021/5/13 3:11
 * @Description 检查Token是否有效
 **/
@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri=request.getRequestURI();
        if(uri.equals("/oauth/token")||uri.equals("/oauth/login")){
            filterChain.doFilter(request,response);
        }else{
            boolean access_token = false;
            boolean authorization = false;
            if(request.getParameter("access_token")==null){
                access_token=true;
            }
            if(request.getHeader("Authorization")==null){
                authorization=true;
            }else{
                if(!request.getHeader("Authorization").startsWith("Bearer")){
                    authorization=true;
                }
            }
            if(access_token && authorization){
                ResultResponse.printError(response,"401","未获得凭证！");
            }else{
                filterChain.doFilter(request,response);
            }
        }
    }
}
