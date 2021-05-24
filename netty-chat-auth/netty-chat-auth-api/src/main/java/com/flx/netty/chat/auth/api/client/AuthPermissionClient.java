package com.flx.netty.chat.auth.api.client;

import com.flx.netty.chat.auth.api.vo.AuthPermissionVO;
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
@FeignClient(name = NETTY_CHAT_AUTH,path = "/auth/permission")
public interface AuthPermissionClient {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<AuthPermissionVO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody AuthPermissionVO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody AuthPermissionVO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<AuthPermissionVO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<AuthPermissionVO>> querySome(@RequestBody Map<String,Object> query, @RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<AuthPermissionVO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<AuthPermissionVO>> queryPage(@RequestBody PageQuery query);

}