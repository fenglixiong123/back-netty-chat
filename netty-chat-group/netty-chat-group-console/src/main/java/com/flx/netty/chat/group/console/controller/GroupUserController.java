package com.flx.netty.chat.group.console.controller;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationResult;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import com.flx.netty.chat.group.api.vo.WebGroupUserVO;
import com.flx.netty.chat.group.console.service.GroupUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2020/8/2 21:36
 * @Description
 **/
@Api(tags = "群用户管理")
@RestController
@RequestMapping("/group/groupUser")
public class GroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(groupUserService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody WebGroupUserVO entityVO){
        try {
            ValidationResult validate = ValidationUtils.validate(entityVO);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            return ResultResponse.success(groupUserService.add(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody WebGroupUserVO entityVO){
        try {
            return ResultResponse.success(groupUserService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(groupUserService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(groupUserService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(groupUserService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/querySome")
    public ResultResponse querySome(@RequestBody Map<String,Object> query,@RequestParam String[] columns){
        try {
            return ResultResponse.success(groupUserService.querySome(query,columns));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(groupUserService.queryAll(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(groupUserService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
