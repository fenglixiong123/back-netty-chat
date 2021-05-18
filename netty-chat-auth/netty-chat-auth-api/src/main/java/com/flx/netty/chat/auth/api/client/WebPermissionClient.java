package com.flx.netty.chat.auth.api.client;

import com.flx.netty.chat.auth.api.vo.WebPermissionVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  FeignClient
*
* @author Fenglixiong
* @since 2021-05-16
*/
@FeignClient(name = "netty-chat-auth",path = "/permission")
public interface WebPermissionClient {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<WebPermissionVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebPermissionVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebPermissionVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<WebPermissionVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<WebPermissionVO>> querySome(@RequestBody Map<String,Object> query, @RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<WebPermissionVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebPermissionVO>> queryPage(@RequestBody PageQuery query);

}