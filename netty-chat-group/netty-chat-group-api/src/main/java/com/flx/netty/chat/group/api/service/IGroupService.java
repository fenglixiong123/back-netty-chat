package com.flx.netty.chat.group.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.group.api.vo.WebGroupVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-group")
public interface IGroupService {

    @GetMapping("/get/{id}")
    ResultResponse<WebGroupVO> get(@PathVariable Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebGroupVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebGroupVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable Long id);

    @PostMapping("/query")
    ResultResponse<List<WebGroupVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/queryAll")
    ResultResponse<List<WebGroupVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebGroupVO>> queryPage(@RequestBody PageQuery query);

}
