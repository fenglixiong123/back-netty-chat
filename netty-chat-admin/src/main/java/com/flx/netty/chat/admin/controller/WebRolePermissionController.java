package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.admin.vo.WebRolePermissionVO;
import com.flx.netty.chat.admin.service.WebRolePermissionService;
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
 *  Controller控制器
 *
 * @author Fenglixiong
 * @since 2021-05-19
 */
@Api
@RestController
@RequestMapping("/webRolePermission")
public class WebRolePermissionController {

    @Autowired
    private WebRolePermissionService webRolePermissionService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(webRolePermissionService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody WebRolePermissionVO entityVO){
        return ValidationUtils.validate(entityVO,()->{
            try {
                return ResultResponse.success(webRolePermissionService.add(entityVO));
            }catch (Exception e){
                return ResultResponse.error(e.getMessage());
            }
        });
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody WebRolePermissionVO entityVO){
        try {
            return ResultResponse.success(webRolePermissionService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(webRolePermissionService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(webRolePermissionService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(webRolePermissionService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(){
        try {
            return ResultResponse.success(webRolePermissionService.queryAll());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(webRolePermissionService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
