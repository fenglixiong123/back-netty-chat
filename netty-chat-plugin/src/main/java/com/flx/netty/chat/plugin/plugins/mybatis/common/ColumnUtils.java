package com.flx.netty.chat.plugin.plugins.mybatis.common;


import com.flx.netty.chat.common.utils.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: fanzhen
 * @Date: 2019/4/8 11:22
 * @Version 2.0.0
 */
public class ColumnUtils {

    public static final List<String> rejectColumn = Arrays.asList("create_user", "update_user", "create_time", "update_time");

    /**
     * 获取对象中的字段并转换成数据库字段
     * @param j
     * @param columns
     * @return
     * @throws Exception
     */
    public static String[] columnSelect(Class j, List<String> columns) throws Exception {
        if(CollectionUtils.isEmpty(columns)){
            return null;
        }
        Field[] fields = j.getDeclaredFields();
        List<String> filedName = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
        filedName = filedName.parallelStream().map(TableFieldAlias::getTableFiledName).collect(Collectors.toList());
        columns = columns.parallelStream().map(TableFieldAlias::getTableFiledName).collect(Collectors.toList());
        List<String> newColumn=new ArrayList<>(columns);
        for (String e : columns) {
            if (!filedName.contains(e)) {
                newColumn.remove(e);
            }
        }
        if (!newColumn.contains("id")) {
            newColumn.add("id");
        }
        String[] array = new String[newColumn.size()];
        return newColumn.toArray(array);
    }

    /**
     * 排除指定字段不参与查询
     * @param j
     * @return
     * @throws Exception
     */
    public static String[] columnReject(Class j) throws Exception {
        return columnReject(j,rejectColumn);
    }

    public static String[] columnReject(Class j, List<String> columns) throws Exception {
        Field[] fields = j.getDeclaredFields();
        List<String> filedName = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
        filedName = filedName.parallelStream().map(TableFieldAlias::getTableFiledName).collect(Collectors.toList());
        filedName.removeAll(columns);
        if (!filedName.contains("id")) {
            filedName.add("id");
        }
        String[] array = new String[filedName.size()];
        return filedName.toArray(array);
    }

}
