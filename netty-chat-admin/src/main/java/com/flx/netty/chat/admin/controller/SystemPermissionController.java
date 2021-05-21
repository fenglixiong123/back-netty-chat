package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.admin.vo.SystemPermissionVO;
import com.flx.netty.chat.admin.service.SystemPermissionService;
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
 * @since 2021-05-21
 */
@Api
@RestController
@RequestMapping("/systemPermission")
public class SystemPermissionController {

    @Autowired
    private SystemPermissionService systemPermissionService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(systemPermissionService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody SystemPermissionVO entityVO){
        return ValidationUtils.validate(entityVO,()->{
            try {
                return ResultResponse.success(systemPermissionService.add(entityVO));
            }catch (Exception e){
                return ResultResponse.error(e.getMessage());
            }
        });
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody SystemPermissionVO entityVO){
        try {
            return ResultResponse.success(systemPermissionService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(systemPermissionService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(systemPermissionService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(systemPermissionService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(){
        try {
            return ResultResponse.success(systemPermissionService.queryAll());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(systemPermissionService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
