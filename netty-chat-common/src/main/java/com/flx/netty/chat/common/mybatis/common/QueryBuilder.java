package com.flx.netty.chat.common.mybatis.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.date.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.flx.netty.chat.common.mybatis.common.TableFieldAlias.getTableFiledName;

/**
 * @author fenglixiong
 * @date 2018-10-17 10:56
 * 条件构造器
 */
@SuppressWarnings("Duplicates")
public class QueryBuilder<T> {

    private static int size = 1000;
    private final Map<String, Object> equalConditionMap = new HashMap<>();
    private final Map<String, Object> neConditionMap = new HashMap<>();
    private final Map<String, String> leftLikeConditionMap = new HashMap<>();
    private final Map<String, String> rightLikeConditionMap = new HashMap<>();
    private List<String> nullConditionList = new ArrayList<>();
    private List<String> notNullConditionList = new ArrayList<>();

    public QueryBuilder<T> id(Long id) {
        Assert.notNull(id, "id can not be null");
        equalConditionMap.put("id", id);
        return this;
    }

    public QueryBuilder<T> neId(Long id) {
        Assert.notNull(id, "id can not be null");
        neConditionMap.put("id", id);
        return this;
    }

    public QueryBuilder<T> neQuery(String property, Object val) {
        neConditionMap.put(property, val);
        return this;
    }

    public QueryBuilder<T> query(String property, Object val) {
        equalConditionMap.put(property, val);
        return this;
    }

    public QueryBuilder<T> leftLike(String property, String val) {
        leftLikeConditionMap.put(property, val);
        return this;
    }

    public QueryBuilder<T> rightLike(String property, String val) {
        rightLikeConditionMap.put(property, val);
        return this;
    }

    public QueryBuilder<T> isNull(String property) {
        nullConditionList.add(property);
        return this;
    }

    public QueryBuilder<T> isNull(List<String> propertyList) {
        nullConditionList.addAll(propertyList);
        return this;
    }

    public QueryBuilder<T> isNotNull(String property) {
        notNullConditionList.add(property);
        return this;
    }

    public QueryBuilder<T> isNotNull(List<String> propertyList) {
        notNullConditionList.addAll(propertyList);
        return this;
    }

    public QueryBuilder<T> query(Object model) throws Exception {
        if (model == null) {
            return this;
        }
        try {
            Field[] fields = model.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            for (String key : fieldNames) {
                String firstLetter = key.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + key.substring(1);
                Object modelValue = model.getClass().getMethod(getter, new Class[]{}).invoke(model);
                if (modelValue != null) {
                    equalConditionMap.put(key, modelValue);
                }
            }
        } catch (Exception e) {
            throw new Exception("Get model name and value fail!" + e);
        }
        return this;
    }

    public QueryBuilder<T> query(Map<String, Object> query) {
        if (null == query) {
            return this;
        }
        Iterator<Map.Entry<String, Object>> iterator = query.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getValue() != null) {
                equalConditionMap.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    private Boolean conditionIsEmpty() throws Exception {
        if (CollectionUtils.isNotEmpty(equalConditionMap)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(neConditionMap)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(leftLikeConditionMap)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(rightLikeConditionMap)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(notNullConditionList)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(nullConditionList)) {
            return false;
        }
        return false;
    }

    @SuppressWarnings("all")
    public QueryWrapper<T> build(Boolean flag) throws Exception {
        QueryWrapper<T> condition = new QueryWrapper<>();
        for (Map.Entry<String, Object> entry : equalConditionMap.entrySet()) {
            if(entry.getValue()==null){
                continue;
            }
            if (entry.getValue() instanceof Date) {
                Date createDate = (Date) entry.getValue();
                condition.ge(getTableFiledName(entry.getKey()), DateUtils.getDayMin(createDate));
                condition.le(getTableFiledName(entry.getKey()), DateUtils.getDayMax(createDate));
            } else if (entry.getValue() instanceof String) {
                if (flag) {
                    condition.like(getTableFiledName(entry.getKey()), entry.getValue());
                } else {
                    condition.eq(getTableFiledName(entry.getKey()), entry.getValue());
                }
            } else if (entry.getValue() instanceof Collection) {
                List select = ((Collection<?>) entry.getValue()).parallelStream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                if (select.size() == 1) {
                    condition.eq(getTableFiledName(entry.getKey()), select.get(0));
                } else if (select.size() > size) {
                    condition.and(q -> {
                        int pageNum = select.size() / size;
                        int remainder = select.size() % size;
                        if (remainder != 0) {
                            pageNum++;
                        }
                        for (int i = 0; i < pageNum; i++) {
                            if (i != 0) {
                                q.or();
                            }
                            if (size * (i + 1) < select.size()) {
                                q.in(getTableFiledName(entry.getKey()), select.subList((size * i), (size * (i + 1))));
                            } else {
                                q.in(getTableFiledName(entry.getKey()), select.subList(size * i, select.size()));
                            }
                        }
                    });
                } else {
                    condition.in(getTableFiledName(entry.getKey()), select);
                }
            }else if(entry.getValue() instanceof Map){
                Map value=(Map) entry.getValue();
                String key=getTableFiledName(entry.getKey());
                if (flag) {
                    for (Object k:value.keySet()){
                        if(value.get(k)==null){
                            continue;
                        }
                        condition.like(key,"\""+k+"\""+":"+"\""+value.get(k));
                    }
                }else {
                    for (Object k:value.keySet()){
                        if(value.get(k)==null){
                            continue;
                        }
                        condition.like(key,"\""+k+"\""+":"+"\""+value.get(k)+"\"");
                    }
                }
            } else {
                condition.eq(getTableFiledName(entry.getKey()), entry.getValue());
            }
        }

        for (Map.Entry<String, Object> entry : neConditionMap.entrySet()) {
            if(entry.getValue()==null){
                continue;
            }

            if (entry.getValue() instanceof Date) {
                Date createDate = (Date) entry.getValue();
                condition.le(getTableFiledName(entry.getKey()), DateUtils.getDayMin(createDate));
                condition.ge(getTableFiledName(entry.getKey()), DateUtils.getDayMax(createDate));
            } else if (entry.getValue() instanceof String) {
                if (flag) {
                    condition.notLike(getTableFiledName(entry.getKey()), entry.getValue());
                } else {
                    condition.ne(getTableFiledName(entry.getKey()), entry.getValue());
                }
            }else if (entry.getValue() instanceof Collection) {
                List select = ((Collection<?>) entry.getValue()).parallelStream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                if (select.size() == 1) {
                    condition.ne(getTableFiledName(entry.getKey()), select.get(0));
                } else if (select.size() > size) {
                    condition.and(q -> {
                        int pageNum = select.size() / size;
                        int remainder = select.size() % size;
                        if (remainder != 0) {
                            pageNum++;
                        }
                        for (int i = 0; i < pageNum; i++) {
                            if (size * (i + 1) < select.size()) {
                                q.notIn(getTableFiledName(entry.getKey()), select.subList((size * i), (size * (i + 1))));
                            } else {
                                q.notIn(getTableFiledName(entry.getKey()), select.subList(size * i, select.size()));
                            }
                        }
                    });
                } else {
                    condition.notIn(getTableFiledName(entry.getKey()), select);
                }
            }else if(entry.getValue() instanceof Map){
                Map value=(Map) entry.getValue();
                String key=getTableFiledName(entry.getKey());
                if(flag){
                    for (Object k:value.keySet()){
                        if(value.get(k)==null){
                            continue;
                        }
                        condition.notLike(key,"\""+k+"\""+":"+"\""+value.get(k));
                    }
                }else {
                    for (Object k:value.keySet()){
                        if(value.get(k)==null){
                            continue;
                        }
                        condition.notLike(key,"\""+k+"\""+":"+"\""+value.get(k)+"\"");
                    }
                }
            } else {
                condition.ne(getTableFiledName(entry.getKey()), entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : leftLikeConditionMap.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                condition.likeLeft(getTableFiledName(entry.getKey()), entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : rightLikeConditionMap.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                condition.likeRight(getTableFiledName(entry.getKey()), entry.getValue());
            }
        }

        nullConditionList = nullConditionList.parallelStream().distinct().filter(Objects::nonNull).collect(Collectors.toList());
        for (String key : nullConditionList) {
            condition.isNull(getTableFiledName(key));
        }

        notNullConditionList = notNullConditionList.parallelStream().distinct().filter(Objects::nonNull).collect(Collectors.toList());
        for (String key : notNullConditionList) {
            condition.isNotNull(getTableFiledName(key));
        }

        return condition;
    }
}