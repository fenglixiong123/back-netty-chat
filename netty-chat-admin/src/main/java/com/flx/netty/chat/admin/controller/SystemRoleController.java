package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.admin.vo.SystemRoleVO;
import com.flx.netty.chat.admin.service.SystemRoleService;
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
@Api(tags = "")
@RestController
@RequestMapping("/systemRole")
public class SystemRoleController {

    @Autowired
    private SystemRoleService systemRoleService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(systemRoleService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody SystemRoleVO entityVO){
        return ValidationUtils.validate(entityVO,()->{
            try {
                return ResultResponse.success(systemRoleService.add(entityVO));
            }catch (Exception e){
                return ResultResponse.error(e.getMessage());
            }
        });
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody SystemRoleVO entityVO){
        try {
            return ResultResponse.success(systemRoleService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(systemRoleService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(systemRoleService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(systemRoleService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(){
        try {
            return ResultResponse.success(systemRoleService.queryAll());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(systemRoleService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
