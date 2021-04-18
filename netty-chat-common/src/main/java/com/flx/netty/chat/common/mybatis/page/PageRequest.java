package com.flx.netty.chat.common.mybatis.page;

import lombok.Setter;

import java.io.Serializable;

/**
 * 分页类
 *
 * @author fanzhen
 * @date 2018-08-13 16:58
 */
public abstract class PageRequest implements Serializable {


    private static final int MAX_PAGE_SIZE = 1000;

    private static final long serialVersionUID = 158898516331352991L;

    /**
     * page number ,starts from 1
     */
    @Setter
    private Integer pageNum;

    /**
     * page size
     */
    @Setter
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum <= 1 ? 1 : pageNum;
    }

    public Integer getPageSize() {
        if (pageSize <= 0) {
            return 10;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        return pageSize;
    }
}
