package com.flx.netty.chat.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Fenglixiong
 * @Create 2018.11.09 23:19
 * @Description
 *
 * getRepeatElements 获取list中重复的值
 * listPageA 对list进行分页操作
 * listPageB 对list进行分页操作
 **/
public class CollectionUtils {

    public static boolean isBlank(String s){
        return StringUtils.isBlank(s);
    }

    public static boolean isNotBlank(String s){
        return !isBlank(s);
    }

    public static boolean isEmpty(List list){
        return (list==null||list.isEmpty());
    }

    public static boolean isNotEmpty(List list){
        return !isEmpty(list);
    }

    public static boolean isEmpty(Set set){
        return (set==null||set.isEmpty());
    }

    public static boolean isNotEmpty(Set set){
        return !isEmpty(set);
    }

    public static boolean isEmpty(Map map){
        return (map==null||map.isEmpty());
    }

    public static boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }

    public static boolean isEmpty(Object[] params){
        return (params==null||params.length==0);
    }

    public static boolean isNotEmpty(Object[] params){
        return !isEmpty(params);
    }

    /**
     * 获取list列表中的重复值
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> getRepeatElements(List<T> list) {
        return list.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // Set<Entry>转换为Stream<Entry>
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(Map.Entry::getKey) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList()); // 转化为 List
    }



}
