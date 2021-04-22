package com.flx.netty.chat.group.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.group.api.vo.WebGroupUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-group",path = "/web/groupUser")
public interface IGroupUserService {

    @GetMapping("/get/{id}")
    ResultResponse<WebGroupUserVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebGroupUserVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebGroupUserVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<WebGroupUserVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<WebGroupUserVO>> querySome(@RequestBody Map<String,Object> query,@RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<WebGroupUserVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebGroupUserVO>> queryPage(@RequestBody PageQuery query);

}
