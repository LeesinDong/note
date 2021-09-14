package com.leesin.java8.wangwenjun.第12讲_collector.rsgp;

import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.leesin.java8.wangwenjun.第12讲_collector.mix.CollectorsAction1_$$$collectingAndThen_averaging$$$_mapping_counting_summing_maxBy_minBy.menu;


/***************************************
 * @author:Alex Wang
 * @Date:2016/10/28 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CollectorsAction4_$$$groupby_partition$$$ {

    public static void main(String[] args) {
        testGroupingByFunction();
        testGroupingByFunctionAndCollector();
        testGroupingByFunctionAndSupplierAndCollector();
        testGroupingByConcurrentWithFunction();
        testGroupingByConcurrentWithFunctionAndCollector();
        testGroupingByConcurrentWithFunctionAndSupplierAndCollector();
        testPartitioningByWithPredicate();
        testPartitioningByWithPredicateAndCollector();
        test();
    }

    /**
     * cr  groupBy
     */
    /**
     * groupBy1 分组，返回值是map<key, List>
     */
    private static void testGroupingByFunction() {
        System.out.println("testGroupingByFunction");
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType))).ifPresent(System.out::println);
    }
    /**
     * groupBy2 分组，返回值是map<key, Collector.xxx之后的值>
     *     例如：collectors.counting()计算每个小组里面的元素的数量 collectors.averagingInt()计算每个小组里面的元素的平均值
     */
    private static void testGroupingByFunctionAndCollector() {
        System.out.println("testGroupingByFunctionAndCollector");
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType,
                        Collectors.averagingInt(Dish::getCalories))))
                .ifPresent(System.out::println);
    }
    /**
     * groupBy3 分组，返回值的Map可以自定义，比如可以自己实现Map，可以是Map的某个实现，比如TreeMap
     */
    private static void testGroupingByFunctionAndSupplierAndCollector() {
        System.out.println("testGroupingByFunctionAndSupplierAndCollector");
        Map<Dish.Type, Double> map = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                TreeMap::new,
                Collectors.averagingInt(Dish::getCalories)));
        Optional.of(map.getClass()).ifPresent(System.out::println);
        Optional.of(map).ifPresent(System.out::println);
    }


    /**
     * groupByConcurrent【和groupBy一样只不过返回的是ConcurrentMap】
     */
    /**
     * groupByConcurrent1 分组
      */
    private static void testGroupingByConcurrentWithFunction() {
        System.out.println("testGroupingByConcurrentWithFunction");
        ConcurrentMap<Dish.Type, List<Dish>> collect = menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType));
        Optional.ofNullable(collect.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(collect).ifPresent(System.out::println);
    }
    /**
     * groupByConcurrent2 分组
     */
    private static void testGroupingByConcurrentWithFunctionAndCollector() {
        System.out.println("testGroupingByConcurrentWithFunctionAndCollector");
        ConcurrentMap<Dish.Type, Double> collect = menu.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, Collectors.averagingInt(Dish::getCalories)));
        Optional.ofNullable(collect).ifPresent(System.out::println);
    }
    /**
     * groupByConcurrent3 分组
     */
    private static void testGroupingByConcurrentWithFunctionAndSupplierAndCollector() {
        System.out.println("testGroupingByConcurrentWithFunctionAndSupplierAndCollector");
        ConcurrentMap<Dish.Type, Double> collect = menu.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(collect).ifPresent(System.out::println);
    }









    /**
     * cr partitionBy 和 groupingBy的区别，这里是supplier
     */
    /**
     * partitionBy1 根据Predicate进行分组，Map<true=[], false=[]>
     */
    private static void testPartitioningByWithPredicate() {
        System.out.println("testPartitioningByWithPredicate");
        Map<Boolean, List<Dish>> collect = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Optional.of(collect).ifPresent(System.out::println);

    }
    /**
     * partitionBy2 分片，Map<true=Collectors操作值, false=Collectors操作值>
     */
    private static void testPartitioningByWithPredicateAndCollector() {
        System.out.println("testPartitioningByWithPredicateAndCollector");
        Map<Boolean, Double> collect = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect).ifPresent(System.out::println);
    }

    public static void test() {
        Map<Boolean, Double> collect = menu.stream().collect(Collectors.partitioningBy((i) -> !i.isVegetarian(), Collectors.averagingInt(Dish::getCalories)));
        System.out.println(collect.toString());

    }}
