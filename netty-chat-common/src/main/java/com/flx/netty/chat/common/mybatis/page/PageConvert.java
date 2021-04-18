package com.flx.netty.chat.common.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public static <T, V> IPage<T> pageConvert(IPage<V> pageEntity) {
        return pageConvert(pageEntity,null);
    }

    public static <T, V> IPage<T> pageConvert(IPage<V> pageEntity, Class<T> classV) {
        Page<T> iPage = new Page<>();
        if(classV!=null) {
            iPage.setRecords(pageEntity.getRecords().parallelStream().map(e -> BeanUtils.copyProperties(e, classV)).collect(Collectors.toList()));
        }
        iPage.setSize(pageEntity.getSize());
        iPage.setCurrent(pageEntity.getCurrent());
        iPage.setPages(pageEntity.getPages());
        iPage.setTotal(pageEntity.getTotal());
        return iPage;
    }

    public static <T, V> IPage<T> pageConvert(IPage<V> pageEntity, Class<T> classV, String split, String fieldName) throws Exception {
        Page<T> iPage = new Page<>();
        iPage.setSize(pageEntity.getSize());
        List<T> newEntityList = new ArrayList<>();
        String setter = "set" + fieldName.trim().substring(0, 1).toUpperCase() + fieldName.substring(1) + "List";
        String getter = "get" + fieldName.trim().substring(0, 1).toUpperCase() + fieldName.substring(1);
        for (V oldEntity : pageEntity.getRecords()) {
            T newEntity = BeanUtils.copyProperties(oldEntity, classV);
            String[] value = oldEntity.getClass().getMethod(getter, new Class[]{}).invoke(oldEntity).toString().split(split);
            Objects.requireNonNull(newEntity).getClass().getMethod(setter, List.class).invoke(newEntity, Arrays.asList(value));
            newEntityList.add(newEntity);
        }
        iPage.setRecords(newEntityList);
        iPage.setCurrent(pageEntity.getCurrent());
        iPage.setPages(pageEntity.getPages());
        iPage.setTotal(pageEntity.getTotal());
        return iPage;
    }

}
