package com.flx.netty.chat.user.console.controller;

import com.flx.netty.chat.common.plugins.mybatis.entity.StateVO;
import com.flx.netty.chat.common.plugins.mybatis.page.QueryAndPage;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.common.utils.validate.ValidationResult;
import com.flx.netty.chat.common.utils.validate.ValidationUtils;
import com.flx.netty.chat.user.api.vo.WebLabelVO;
import com.flx.netty.chat.user.console.service.LabelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2020/8/2 21:36
 * @Description
 **/
@Api(tags = "好友标签管理")
@RestController
@RequestMapping("/web/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/get/{id}")
    public ResultResponse get(@PathVariable Long id){
        try {
            return ResultResponse.success(labelService.get(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResultResponse add(@RequestBody WebLabelVO entity){
        try {
            ValidationResult validate = ValidationUtils.validate(entity);
            if(!validate.isSuccess()){
                return validate.toResponse();
            }
            return ResultResponse.success(labelService.add(entity));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResultResponse update(@RequestBody WebLabelVO entity){
        try {
            return ResultResponse.success(labelService.update(entity));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PutMapping("/updateState")
    public ResultResponse updateState(@RequestBody StateVO stateVO){
        try {
            return ResultResponse.success(labelService.updateState(stateVO));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable Long id){
        try {
            return ResultResponse.success(labelService.delete(id));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResultResponse query(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(labelService.query(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryAll")
    public ResultResponse queryAll(@RequestBody Map<String,Object> query){
        try {
            return ResultResponse.success(labelService.queryAll(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

    @PostMapping("/queryPage")
    public ResultResponse queryPage(@RequestBody QueryAndPage query){
        try {
            return ResultResponse.success(labelService.queryPage(query));
        }catch (Exception e){
            return ResultResponse.error(e.getMessage());
        }
    }

}
