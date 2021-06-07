package com.flx.netty.chat.admin.controller;

import com.flx.netty.chat.admin.service.SystemMenuService;
import com.flx.netty.chat.admin.vo.SystemMenuVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 菜单信息 Controller控制器
 *
 * @author Fenglixiong
 * @since 2021-05-28
 */
@Api(tags = "菜单信息")
@RestController
@RequestMapping("admin/menu")
public class SystemMenuController {

    @Autowired
    private SystemMenuService systemMenuService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(systemMenuService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody SystemMenuVO entityVO){
        return ValidationUtils.validate(entityVO,()->{
            try {
                return ResultResponse.success(systemMenuService.add(entityVO));
            }catch (Exception e){
                return ResultResponse.error(e.getMessage());
            }
        });
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody SystemMenuVO entityVO){
        try {
            return ResultResponse.success(systemMenuService.update(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody UpdateState entityVO){
        try {
            return ResultResponse.success(systemMenuService.updateState(entityVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(systemMenuService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(systemMenuService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(){
        try {
            return ResultResponse.success(systemMenuService.queryAll());
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody PageQuery query){
        try {
            return ResultResponse.success(systemMenuService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
