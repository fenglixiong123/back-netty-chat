package com.flx.netty.chat.admin.console.controller;

import com.flx.netty.chat.admin.console.service.WebUserService;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationResult;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import com.flx.netty.chat.user.api.vo.ValidatePassVO;
import com.flx.netty.chat.user.api.vo.WebUserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2020/8/2 21:36
 * @Description
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/web/user")
public class WebUserController {

    @Autowired
    private WebUserService userService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(userService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody WebUserVO entityVO){
        try {
            ValidationResult validate = ValidationUtils.validate(entityVO);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            return ResultResponse.success(userService.add(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody WebUserVO entityVO){
        try {
            return ResultResponse.success(userService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(userService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(userService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(userService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(userService.queryAll(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(userService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @GetMapping("/getByUsername")
    public ResultResponse getByUsername(@RequestParam String username){
        try {
            return ResultResponse.success(userService.getByUsername(username));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @GetMapping("/existByUsername")
    public ResultResponse existByUsername(@RequestParam String username){
        try {
            return ResultResponse.success(userService.isExist(username));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/validateUser")
    public ResultResponse validateUser(@RequestBody ValidatePassVO entityVO){
        try {
            ValidationResult result = ValidationUtils.validate(entityVO);
            if(!result.isSuccess()){
                return result.toResponse();
            }
            return ResultResponse.success(userService.validateUser(entityVO.getUsername(),entityVO.getPassword()));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
