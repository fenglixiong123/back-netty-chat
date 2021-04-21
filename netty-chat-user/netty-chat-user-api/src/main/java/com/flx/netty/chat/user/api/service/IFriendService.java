package com.flx.netty.chat.user.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.user.api.vo.WebFriendVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-user")
interface IFriendService {

    @GetMapping("/get/{id}")
    ResultResponse<WebFriendVO> get(@PathVariable Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebFriendVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebFriendVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable Long id);

    @PostMapping("/query")
    ResultResponse<List<WebFriendVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/queryAll")
    ResultResponse<List<WebFriendVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebFriendVO>> queryPage(@RequestBody PageQuery query);

}
