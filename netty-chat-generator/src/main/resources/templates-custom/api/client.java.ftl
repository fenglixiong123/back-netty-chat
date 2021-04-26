package ${cfg.parentPackage}.api.client;

import ${cfg.parentPackage}.api.vo.${entity}VO;

import com.flx.netty.chat.common.entity.UpdateState;
import com.flx.netty.chat.common.utils.page.PageQuery;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.result.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* ${table.comment!} FeignClient
*
* @author ${author}
* @since ${date}
*/
@FeignClient(name = "${cfg.parentModule!}",path = "<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/${table.entityPath}")
public interface ${entity}Client {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    ResultResponse<${entity}VO> get(@PathVariable(value = "id") Long id);

    @PostMapping("/add")
    ResultResponse<Long> add(@RequestBody ${entity}VO entityVO);

    @PutMapping("/update")
    ResultResponse<Integer> update(@RequestBody ${entity}VO entityVO);

    @PutMapping("/updateState")
    ResultResponse<Boolean> updateState(@RequestBody UpdateState entityVO);

    @DeleteMapping("/delete/{id}")
    ResultResponse<Integer> delete(@PathVariable(value = "id") Long id);

    @PostMapping("/query")
    ResultResponse<List<${entity}VO>> query(@RequestBody Map<String,Object> query);

    @PostMapping("/querySome")
    ResultResponse<List<${entity}VO>> querySome(@RequestBody Map<String,Object> query,@RequestParam(value = "columns") String[] columns);

    @PostMapping("/queryAll")
    ResultResponse<List<${entity}VO>> queryAll(@RequestBody Map<String,Object> query);

    @PostMapping("/queryPage")
    ResultResponse<PageVO<${entity}VO>> queryPage(@RequestBody PageQuery query);

}