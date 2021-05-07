package com.flx.netty.chat.auth.console.controller;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationResult;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import com.flx.netty.chat.auth.api.vo.LoginVO;
import com.flx.netty.chat.auth.console.service.LoginService;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/13 15:39
 * @Description:
 */
@Slf4j
@Api(tags = "登录管理")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResultResponse add(@RequestBody LoginVO entity){
        try {
            ValidationResult validate = ValidationUtils.validate(entity);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            loginService.login(entity);
            log.info("登录成功：username = "+entity.getUsername());
            return ResultResponse.success("恭喜您，登录成功！");
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResultResponse register(@RequestBody WebUserVO entityVO){
        try {
            ValidationResult validate = ValidationUtils.validate(entityVO);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            Long id = loginService.register(entityVO);
            log.info("注册成功：id = "+id);
            return ResultResponse.success("恭喜您，注册成功！");
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
