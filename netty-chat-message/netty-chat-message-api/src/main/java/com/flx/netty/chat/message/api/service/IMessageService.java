package com.flx.netty.chat.message.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.message.api.vo.WebMessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-message",path = "/web/message")
public interface IMessageService {

    @GetMapping("/get/{id}")
    ResultResponse<WebMessageVO> get(@PathVariable Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebMessageVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebMessageVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable Long id);

    @PostMapping("/query")
    ResultResponse<List<WebMessageVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/queryAll")
    ResultResponse<List<WebMessageVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebMessageVO>> queryPage(@RequestBody PageQuery query);

}
