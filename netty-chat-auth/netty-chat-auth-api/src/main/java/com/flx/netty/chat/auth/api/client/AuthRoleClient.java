package com.flx.netty.chat.auth.api.client;

import com.flx.netty.chat.auth.api.vo.AuthRoleVO;
import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.openfeign.constants.ServiceConstant.NETTY_CHAT_AUTH;

/**
*  FeignClient
*
* @author Fenglixiong
* @since 2021-05-16
*/
@FeignClient(name = NETTY_CHAT_AUTH,path = "/auth/role")
public interface AuthRoleClient {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<AuthRoleVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody AuthRoleVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody AuthRoleVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<AuthRoleVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<AuthRoleVO>> querySome(@RequestBody Map<String,Object> query, @RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<AuthRoleVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<AuthRoleVO>> queryPage(@RequestBody PageQuery query);

}