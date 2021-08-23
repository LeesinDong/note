package com.leesin.java8.wangwenjun.第4讲_方法引用_函数推导_consumer例子;

/**
 * Created by wangwenjun on 2016/10/16.
 */
@FunctionalInterface
public interface ThreeFunction<T, U, K, R> {

    R apply(T t, U u, K k);

}
