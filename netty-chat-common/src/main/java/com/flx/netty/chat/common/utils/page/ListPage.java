package com.flx.netty.chat.common.utils.page;

import com.flx.netty.chat.common.utils.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/30 22:09
 * @Description:
 */
public class ListPage {

    /**
     * 返回list分页后的数据
     */
    public static <T> List<T> getPage(List<T> dataList,int page,int pageSize){
        return dataList.stream().skip((page-1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    /**
     * 返回list分页后的数据
     */
    public static <T> List<T> getPage0(List<T> dataList, int curPage, int pageSize) {
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        int fromIndex; // 开始索引
        int endIndex; // 结束索引
        int totalSize = dataList.size(); // 记录总数
        int totalPage = PageUtils.getTotalPage(totalSize,pageSize); // 页数
        if (curPage != totalPage) {
            fromIndex = (curPage - 1) * pageSize;
            endIndex = fromIndex + pageSize;
        } else {
            fromIndex = (curPage - 1) * pageSize;
            endIndex = totalSize;
        }

        return dataList.subList(fromIndex, endIndex);

    }

}
