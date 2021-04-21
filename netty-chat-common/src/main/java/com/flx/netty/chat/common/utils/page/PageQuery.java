package com.flx.netty.chat.common.utils.page;

import lombok.Data;

import java.util.Map;

/**
 * @author fenglixiong
 * @date2018-08-22-10:13
 */
@Data
public class PageQuery extends SimplePage {

    private Map<String, Object> query;

}
