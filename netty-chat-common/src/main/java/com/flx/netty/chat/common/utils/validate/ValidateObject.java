package com.flx.netty.chat.common.utils.validate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/30 22:18
 * @Description:
 */
public class ValidateObject {

    /**
     * 验证Map
     * 仅仅支持 List,Map,String,Long,Integer,Double,Date,Boolean
     * @param payload
     * @return
     */
    public boolean validateMapValue(Map<String, ?> payload) {
        for (Map.Entry<String, ?> entry : payload.entrySet()) {
            String key = entry.getKey();
            Objects.requireNonNull(key);
            Object value = entry.getValue();
            if (value instanceof List && !validateList((List<?>) value)) {
                return false;
            } else if (value instanceof Map && !validateMap((Map<?, ?>) value)) {
                return false;
            } else if (value != null && !isSupportedType(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证Collection的Value是否是基础类型
     * @param value
     * @return
     */
    public static boolean isSupportedType(Object value) {
        if (value instanceof List) {
            return validateList((List<?>) value);
        } else if (value instanceof Map) {
            return validateMap((Map<?, ?>) value);
        } else {
            return isBasicType(value);
        }
    }

    /**
     * 验证Map的Value是否基础类型
     * @param map
     * @return
     */
    public static boolean validateMap(Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value == null || !isSupportedType(value)) {
                return false;
            }

            if (entry.getKey() == null || !(entry.getKey() instanceof String)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证list的value是否是基础类型
     * @param list
     * @return
     */
    public static boolean validateList(List<?> list) {
        for (Object object : list) {
            if (object != null && !isSupportedType(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证Object是否是基础类型
     * @param value
     * @return
     */
    public static boolean isBasicType(Object value) {
        Class<?> c = value.getClass();

        if (c.isArray()) {
            return c == Integer[].class || c == Long[].class || c == String[].class;
        }
        return c == String.class || c == Integer.class || c == Long.class || c == Double.class || c == Date.class || c == Boolean.class;
    }

}
