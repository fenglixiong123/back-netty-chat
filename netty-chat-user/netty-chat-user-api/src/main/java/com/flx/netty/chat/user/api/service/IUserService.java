package com.flx.netty.chat.user.api.service;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import com.flx.netty.chat.user.api.vo.WebUserVO;
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
public interface IUserService {

    @GetMapping("/get/{id}")
    ResultResponse<WebUserVO> get(@PathVariable Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebUserVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebUserVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable Long id);

    @PostMapping("/query")
    ResultResponse<List<WebUserVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/queryAll")
    ResultResponse<List<WebUserVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebUserVO>> queryPage(@RequestBody PageQuery query);

}
