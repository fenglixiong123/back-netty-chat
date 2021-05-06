package com.flx.netty.chat.auth.api.service;

import com.flx.netty.chat.auth.api.vo.ValidatePassVO;
import com.flx.netty.chat.auth.api.vo.WebUserVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/21 20:32
 * @Description:
 */
@FeignClient(name = "netty-chat-auth",path = "/auth/user")
public interface IUserService {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<WebUserVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody WebUserVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody WebUserVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<WebUserVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<WebUserVO>> querySome(@RequestBody Map<String,Object> query, @RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<WebUserVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<WebUserVO>> queryPage(@RequestBody PageQuery query);

    @GetMapping("/getByUsername")
    ResultResponse<WebUserVO> getByUsername(@RequestParam(value = "username") String username);

    @GetMapping("/existByUsername")
    ResultResponse<Boolean> existByUsername(@RequestParam(value = "username") String username);

    @PostMapping("/validateUser")
    ResultResponse<WebUserVO> validateUser(@RequestBody ValidatePassVO entityVO);

}
