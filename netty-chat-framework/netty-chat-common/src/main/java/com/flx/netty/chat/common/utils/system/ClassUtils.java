package com.flx.netty.chat.common.utils.system;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/20 12:44
 * @Description:
 */
public class ClassUtils {

    public static void main(String[] args) {

    }

    /**
     * 返回类的指定泛型的具体类型
     *
     * getSuperclass  返回直接继承的父类（由于编译擦除，没有显示泛型参数）  class cn.test.Person
     * getGenericSuperclass  返回直接继承的父类（包含泛型参数）  cn.test.Person<cn.test.Test>
     *
     * @param clazz 你要获取父类泛型的类，一般填this.getClass
     * @param index 你要获取哪个泛型，按照顺序索引
     * @return 返回泛型的具体Class类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperGenericClass(Class clazz,int index)throws Exception{
        //获取该类的直接超类[带泛型]
        Type genType = clazz.getGenericSuperclass();
        //判断是否支持泛型
        if(!(genType instanceof ParameterizedType)){
            throw new Exception("can't support generic !");
        }
        //ParameterizedType 是Type的子类，用于接收含有泛型的类型
        //获取实际类型参数的Type对象数组，数组放的是Class，比如 Jack extends Person<A,B>
        //比如当前类为BaseManager<T extends BaseDO,V extends BaseDao>
        //实际用的时候可能会写StudentManager extends BaseManager{...}
        //此时对于StudentManager来说
        //this.getClass是当前类
        //this.getClass.getGenericSuperclass()是BaseManager
        //arguments为：[T.class,V.class]
        Type[] arguments = ((ParameterizedType) genType).getActualTypeArguments();
        if(index>=arguments.length||index<0){
            throw new Exception("index error !");
        }
        return (Class<T>)arguments[index];
    }

    /**
     * 获取父类泛型对应的对象
     */
    public static <T> T getSuperGenericInstance(Class<T> clazz,int index) throws Exception {
        Class<T> genericClass = getSuperGenericClass(clazz,index);
        return genericClass.newInstance();
    }

}
