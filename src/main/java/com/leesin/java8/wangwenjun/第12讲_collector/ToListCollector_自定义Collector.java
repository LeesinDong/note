package com.leesin.java8.wangwenjun.第12讲_collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/30 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class ToListCollector_自定义Collector<T> implements Collector<T, List<T>, List<T>> {

    /**
     *     Collector 源码（collect(), toList()等）
     *
     *     Collector<T, A, R> T stream里面的类型，A 累加器的容器， R返回值
     *
     *     Supplier<A> supplier(); 提供累加器容器的方法
     *     BiConsumer<A, T> accumulator(); 累加器
     *     BinaryOperator<A> combiner(); 并发方法，将每个累加器merge回来
     *     Function<A, R> finisher(); 完成的时候
     *     Set<Characteristics> characteristics(); 特征值
     */


    private void log(final String log) {

        System.out.println(Thread.currentThread().getName() + "-" + log);
    }

    /**
     * 容器提供
     */
    @Override
    public Supplier<List<T>> supplier() {
        log("supplier");
        return ArrayList::new;
    }

    /**
     * 累加操作
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        log("accumulator");
        return List::add;
    }

    /**
     * merge操作
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        log("combiner");
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 完成的时候
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        log("finisher");
        /**
         * 等价于：return t -> t; 是什么就是什么
         */
        return Function.identity();
    }

    /**
     * 特征值
     */
    @Override
    public Set<Characteristics> characteristics() {
        log("characteristics");
        return Collections.unmodifiableSet(
                //                                                          是否是并发容器
                EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT
                ));
    }
}