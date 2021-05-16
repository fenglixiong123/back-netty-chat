package com.flx.netty.chat.auth.api.client;

import com.flx.netty.chat.auth.api.vo.RoleVO;

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
@FeignClient(name = "netty-chat-auth",path = "/role")
public interface RoleClient {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<RoleVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody RoleVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody RoleVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<RoleVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<RoleVO>> querySome(@RequestBody Map<String,Object> query,@RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<RoleVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<RoleVO>> queryPage(@RequestBody PageQuery query);

}