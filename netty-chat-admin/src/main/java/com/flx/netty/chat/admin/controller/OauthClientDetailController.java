package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.admin.vo.OauthClientDetailVO;
import com.flx.netty.chat.admin.service.OauthClientDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationResult;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import com.flx.netty.chat.common.entity.UpdateState;
import java.util.Map;

/**
 * 客户端信息表 Controller控制器
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Api
@RestController
@RequestMapping("/oauthClientDetail")
public class OauthClientDetailController {

    @Autowired
    private OauthClientDetailService oauthClientDetailService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(oauthClientDetailService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody OauthClientDetailVO entityVO){
        try {
            ValidationResult validate = ValidationUtils.validate(entityVO);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            return ResultResponse.success(oauthClientDetailService.add(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody OauthClientDetailVO entityVO){
        try {
            return ResultResponse.success(oauthClientDetailService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(oauthClientDetailService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(oauthClientDetailService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(oauthClientDetailService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(){
        try {
            return ResultResponse.success(oauthClientDetailService.queryAll());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(oauthClientDetailService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
