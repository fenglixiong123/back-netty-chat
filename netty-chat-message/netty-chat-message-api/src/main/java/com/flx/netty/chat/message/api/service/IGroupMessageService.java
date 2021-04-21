package com.flx.netty.chat.message.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.message.api.vo.WebGroupMessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-message",path = "/web/groupMessage")
public interface IGroupMessageService {

    @GetMapping("/get/{id}")
    ResultResponse<WebGroupMessageVO> get(@PathVariable Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebGroupMessageVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebGroupMessageVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable Long id);

    @PostMapping("/query")
    ResultResponse<List<WebGroupMessageVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/queryAll")
    ResultResponse<List<WebGroupMessageVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebGroupMessageVO>> queryPage(@RequestBody PageQuery query);

}
