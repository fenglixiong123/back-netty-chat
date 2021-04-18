package com.flx.netty.chat.common.mybatis.page;

import lombok.Data;

import java.util.Map;

/**
 * @author fenglixiong
 * @date2018-08-22-10:13
 */
@Data
public class QueryAndPage extends PageRequest {

    private Map<String, Object> query;

}
