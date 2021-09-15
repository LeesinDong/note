package com.leesin.java8.wangwenjun.第12讲_collector.mix;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/29 QQ:532500648
 * QQ交流群:286081824
 ***************************************/

import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.leesin.java8.wangwenjun.第12讲_collector.mix.CollectorsAction1_$$$collectingAndThen_averaging$$$_mapping_counting_summing_maxBy_minBy.menu;


/**
 *
 */
public class CollectorsAction2_$$$toCollection_toList_toSet_toMap_toConcurrentMap$$$ {

    public static void main(String[] args) {
        testToCollection();
        testToConcurrentMap();
        testToConcurrentMapWithBinaryOperator();
        testToConcurrentMapWithBinaryOperatorAndSupplier();

        testToList();
        testToSet();

        testToMap();
        testToMapWithBinaryOperator();
        testToMapWithBinaryOperatorAndSupplier();
    }





    /**
     * cr toCollection (里面是实现)
     */
    private static void testToCollection() {
        System.out.println("testToCollection");
        Optional.of(menu.stream().filter(d -> d.getCalories() > 600).collect(Collectors.toCollection(LinkedList::new)))
                .ifPresent(System.out::println);
    }







    /**
     * cr toList
     */
    private static void testToList() {
        Optional.of(menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList()))
                .ifPresent(r -> {
                    System.out.println(r.getClass());
                    System.out.println(r);
                });
    }





    /**
     * cr toSet
     */
    private static void testToSet() {
        Optional.of(menu.stream().filter(Dish::isVegetarian).collect(Collectors.toSet()))
                .ifPresent(r -> {
                    System.out.println(r.getClass());
                    System.out.println(r);
                });
    }





    /**
     * toMap和上面的toConcurrentMap一样的操作，只是换了名字
     */
    /**
     * cr toMap1
     */
    private static void testToMap() {
        System.out.println("testToMap");
        /**
         * 线程安全的用HashMap, 这里只是替换成了synchronizedMap, 效率是没有chm高的，只是举个栗子：HashMap线程安全的方式
         */
        Optional.of(menu.stream().collect(
                Collectors.collectingAndThen(
                        // cr
                        Collectors.toMap(Dish::getName, Dish::getCalories),
                        Collections::synchronizedMap))
        )
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread key = entry.getKey();
            StackTraceElement[] value = entry.getValue();

            if (key.getId() != Thread.currentThread().getId()) {
                continue;
            }
            System.out.println("=========="+key.getName());
            for (StackTraceElement ste : value) {
                if(ste.isNativeMethod()) {
                    continue;
                }
                System.out.println(ste.getClassName());
                System.out.println("isNativeMethod>" + ste.isNativeMethod());
                System.out.println(ste.getMethodName());
                System.out.println(ste.getLineNumber());
                System.out.println(ste.getFileName());
            }
        }
    }
    /**
     * cr toMap1
     * Type:Total
     */
    private static void testToMapWithBinaryOperator() {
        System.out.println("testToMapWithBinaryOperator");
        Optional.of(menu.stream()
                .collect(Collectors.toMap(Dish::getType, v -> 1L, (a, b) -> a + b)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }
    /**
     * cr toMap1
     * Type:Total
     */
    private static void testToMapWithBinaryOperatorAndSupplier() {
        System.out.println("testToMapWithBinaryOperatorAndSupplier");
        Optional.of(menu.stream()
                .collect(Collectors.toMap(Dish::getType, v -> 1L, (a, b) -> a + b, Hashtable::new)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }





    /**
     * toConcurrentMap
     */
    /**
     * cr toConcurrentMap1（key, value）
     */
    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap");
        Optional.of(menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }
    /**
     * cr toConcurrentMap2（key, value, mergeFunction）
     * 当前例子功能：Type:Total   即：每个类型的数量
     */
    private static void testToConcurrentMapWithBinaryOperator() {
        System.out.println("testToConcurrentMapWithBinaryOperator");
        Optional.of(menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    @Test
    public void test(){
        List<Integer> es = Lists.newArrayList(1, 2, 3, 1);
        ConcurrentMap<Integer, Integer> collect = es.stream().collect(Collectors.toConcurrentMap(i -> i, i -> i, (a, b) -> a + b));
        System.out.println(collect);
    }
    /**
     * cr toConcurrentMap3（key, value, mergeFunction，mapFactory）
     * Type:Total
     */
    private static void testToConcurrentMapWithBinaryOperatorAndSupplier() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

        System.out.println("testToConcurrentMapWithBinaryOperatorAndSupplier");
        Optional.of(menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b, ConcurrentSkipListMap::new)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

}
