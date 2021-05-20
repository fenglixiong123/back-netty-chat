package com.flx.netty.chat.admin.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flx.netty.chat.common.utils.page.PageVO;
import com.flx.netty.chat.common.utils.servlet.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fenglixiong
 * @date2018-10-17-11:20 分页工具转换类
 */
public class PageConvert {

    public static <T, V> PageVO<T> pageConvert(IPage<V> iPage) {
        return pageConvert(iPage,null);
    }

    public static <T, V> PageVO<T> pageConvert(IPage<V> iPage, Class<T> classV) {
        PageVO<T> pageVO = new PageVO<>();
        if(classV!=null) {
            pageVO.setRecords(iPage.getRecords().parallelStream().map(e -> BeanUtils.copyProperties(e, classV)).collect(Collectors.toList()));
        }
        pageVO.setSize(iPage.getSize());
        pageVO.setCurrent(iPage.getCurrent());
        pageVO.setPages(iPage.getPages());
        pageVO.setTotal(iPage.getTotal());
        return pageVO;
    }

    /**
     * 分页数据转换
     * 比如：
     * hobby:1,2,3,4,5
     * 转换为：
     * hobbyList:[1,2,3,4,5]
     * @param iPage
     * @param classV
     * @param split
     * @param fieldName
     * @param <T>
     * @param <V>
     * @return
     * @throws Exception
     */
    public static <T, V> PageVO<T> pageConvert(IPage<V> iPage, Class<T> classV, String split, String fieldName) throws Exception {
        PageVO<T> pageVO = new PageVO<>();
        iPage.setSize(iPage.getSize());
        List<T> newEntityList = new ArrayList<>();
        String setter = "set" + fieldName.trim().substring(0, 1).toUpperCase() + fieldName.substring(1) + "List";
        String getter = "get" + fieldName.trim().substring(0, 1).toUpperCase() + fieldName.substring(1);
        for (V oldEntity : iPage.getRecords()) {
            T newEntity = BeanUtils.copyProperties(oldEntity, classV);
            String[] value = oldEntity.getClass().getMethod(getter, new Class[]{}).invoke(oldEntity).toString().split(split);
            Objects.requireNonNull(newEntity).getClass().getMethod(setter, List.class).invoke(newEntity, Arrays.asList(value));
            newEntityList.add(newEntity);
        }
        pageVO.setRecords(newEntityList);
        pageVO.setCurrent(iPage.getCurrent());
        pageVO.setPages(iPage.getPages());
        pageVO.setTotal(iPage.getTotal());
        return pageVO;
    }

}
