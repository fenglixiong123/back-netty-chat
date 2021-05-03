package com.flx.netty.chat.generator.controller;

import com.flx.netty.chat.common.utils.json.JsonUtils;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.generator.output.CustomFileOutput;
import com.flx.netty.chat.generator.output.SimpleFileOutput;
import com.flx.netty.chat.generator.entity.ConfigInfo;
import com.flx.netty.chat.generator.service.CustomGeneratorService;
import com.flx.netty.chat.generator.service.SimpleGeneratorService;
import com.flx.netty.chat.generator.service.TableService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/26 13:54
 * @Description:
 */
@Slf4j
@Api(tags = "生成代码控制器")
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private CustomGeneratorService customService;
    @Autowired
    private SimpleGeneratorService simpleService;
    @Autowired
    private TableService tableService;

    /**
     * 简单生成代码
     */
    @PostMapping("/simple")
    public ResultResponse simple(@RequestBody ConfigInfo config){
        try {
            log.info("SimpleGenerator params : {}",JsonUtils.toJsonMsg(config));
            simpleService.generator(config);
            return ResultResponse.success(SimpleFileOutput.getPathMap());
        }catch (Exception e){
            return ResultResponse.error();
        }
    }

    /**
     * 定制化生成代码
     */
    @PostMapping("/custom")
    public ResultResponse custom(@RequestBody ConfigInfo config){
        try {
            log.info("CustomGenerator params : {}",JsonUtils.toJsonMsg(config));
            customService.generator(config);
            return ResultResponse.success(CustomFileOutput.getPathMap());
        }catch (Exception e){
            return ResultResponse.error();
        }
    }

    @GetMapping("/tables")
    public ResultResponse tables(@RequestParam String ip,
                                 @RequestParam(required = false,defaultValue = "3306") int port,
                                 @RequestParam String database,
                                 @RequestParam String username,
                                 @RequestParam String password){
        try{
            log.info(">>>>>>>getTables ip:{},port:{},database:{},username:{},password:{}",ip,port,database,username,password);
            return ResultResponse.success(tableService.getTables(ip,port,database,username,password));
        }catch (Exception e){
            return ResultResponse.error();
        }
    }

}
