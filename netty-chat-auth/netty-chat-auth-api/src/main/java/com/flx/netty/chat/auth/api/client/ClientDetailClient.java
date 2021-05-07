package com.flx.netty.chat.auth.api.client;

import com.flx.netty.chat.auth.api.vo.ClientDetailVO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* 客户端信息表 FeignClient
*
* @author Fenglixiong
* @since 2021-05-07
*/
@FeignClient(name = "netty-chat-auth",path = "/clientDetail")
public interface ClientDetailClient {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<ClientDetailVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody ClientDetailVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody ClientDetailVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<ClientDetailVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<ClientDetailVO>> querySome(@RequestBody Map<String,Object> query,@RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<ClientDetailVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<ClientDetailVO>> queryPage(@RequestBody PageQuery query);

}