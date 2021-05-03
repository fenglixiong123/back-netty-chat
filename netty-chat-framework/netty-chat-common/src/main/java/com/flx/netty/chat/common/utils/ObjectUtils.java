package com.flx.netty.chat.common.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/8 18:37
 * @Description:
 *
 * 获取Class的三种方式
 * Class stuClass = Class.forName("com.flx.Student");
 * Student.class
 * student.getClass
 *
 * Field是字段，是跟对象无关的，通过Class来获取的
 * Class操作
 *      getFields() 获取所有公有字段
 *      getDeclaredFields() 获取所有包含公有、私有、保护字段
 *      getField(String fieldName) 获取指定公有字段
 *      getDeclaredField(String fieldName) 获取指定字段包含公有私有保护
 * 对象值操作
 *      get(Object obj) 获取对象obj字段的字段值
 *      set(Object obj,Object value) 设置对象obj字段的字段值
 * 成员方法操作：
 *      getMethods() 获取所有公共方法
 *      getDeclaredMethods() 获取所有方法包含公共和私有保护
 *      getMethod(String name,Class<?>... parameterTypes) 获取指定公共方法，跟参数类型
 *      getDeclaredMethod(String name,Class<?>... parameterTypes) 获取指定任意方法，跟参数类型
 *      invoke(Object obj,Object... args) 调用obj对象的方法
 *
 * getFieldByName 通过字段名获取Field
 * getFieldValueByName 通过名字获取字段值
 * getFieldValueByCascadeName 通过字段名获取字段值（可以是级联属性）
 * setFieldValueByName 通过字段名设置字段值
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    @Getter
    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    /**
     * 判断是否是基本数据类型
     */
    public static boolean isBaseType(Object value){
        String typeName = value.getClass().getName();
        for (String s:types) {
            if(s.equals(typeName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param entity 对象
     * @param cascadeFieldName 带路径的属性名或简单属性名
     * @return 属性值
     * @MethodName : getFieldValueByCascadeName
     * @Description :
     * 根据带路径或不带路径的属性名获取属性值
     * 即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
     */
    public static Object getFieldValueByCascadeName(Object entity,String cascadeFieldName) throws Exception {

        String[] attributes = cascadeFieldName.split("\\.");
        if (attributes.length == 1) {
            return getFieldValueByName(entity,cascadeFieldName);
        } else {
            Object fieldObj = getFieldValueByName(entity,attributes[0]);
            String subFieldNameSequence = cascadeFieldName.substring(cascadeFieldName.indexOf(".") + 1);
            return getFieldValueByCascadeName(fieldObj,subFieldNameSequence);
        }

    }

    /**
     * @param o 对象
     * @param fieldName 字段名
     * @return 字段值
     * @MethodName : getFieldValueByName
     * @Description : 根据字段名获取字段值
     */
    public static Object getFieldValueByName(Object o,String fieldName) throws Exception {

        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            return field.get(o);
        } else {
            throw new Exception("The class "+ o.getClass().getSimpleName() +" does not exist field name "+ fieldName +"!");
        }

    }

    /**
     * @param entity  对象
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @MethodName : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     */
    public static void setFieldValueByName(Object entity, String fieldName, Object fieldValue) throws Exception {

        Field field = getFieldByName(fieldName, entity.getClass());
        if (field != null) {
            field.setAccessible(true);
            //获取字段类型
            Class<?> fieldType = field.getType();

            //根据字段类型给字段赋值
            String value = fieldValue + "";
            value = value.trim();
            if (String.class == fieldType) {
                field.set(entity, value);
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                if (StringUtils.isEmpty(value)) {
                    field.set(entity, null);
                } else {
                    field.set(entity, Integer.parseInt(value));
                }
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                if (StringUtils.isEmpty(value)) {
                    field.set(entity, null);
                } else {
                    field.set(entity, Long.valueOf(value));
                }
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                if (StringUtils.isEmpty(value)) {
                    field.set(entity, null);
                } else {
                    field.set(entity, Float.valueOf(value));
                }
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                if (StringUtils.isEmpty(value)) {
                    field.set(entity, null);
                } else {
                    field.set(entity, Short.valueOf(value));
                }
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                if (StringUtils.isEmpty(value)) {
                    field.set(entity, null);
                } else {
                    field.set(entity, Double.valueOf(value));
                }
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(entity, fieldValue.toString().charAt(0));
                }
            } else if (Date.class == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(entity, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
                }
            } else if (fieldType.isEnum()) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    Method m = fieldType.getMethod("valueOf", String.class);
                    Object writeValue = m.invoke(entity, value);
                    field.set(entity, writeValue);
                }
            } else {
                field.set(entity, fieldValue);
            }
        } else {
            throw new Exception("The class " + entity.getClass().getSimpleName() +" does not exist field name "+fieldName+"!");
        }
    }

    public static <T> void setFieldByName(T t, String fieldName, Object fieldValue) throws Exception {
        Field field = t.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(t,fieldValue);
    }

    public static <T> void setFieldByNameIgnoreException(T t, String fieldName, Object fieldValue){
        try {
            Field field = t.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(t, fieldValue);
        }catch (Exception e){
            //todo@ignore
        }
    }

    /**
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     * @Description : 根据字段名获取字段,可以包含继承关系
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {

        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }
        return null;
    }

}
