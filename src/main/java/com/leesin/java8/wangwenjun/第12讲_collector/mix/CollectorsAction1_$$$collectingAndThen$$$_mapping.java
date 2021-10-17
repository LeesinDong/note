package com.leesin.java8.wangwenjun.第12讲_collector.mix;

import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;

import java.util.*;
import java.util.stream.Collectors;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/27 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class
CollectorsAction1_$$$collectingAndThen$$$_mapping {


    public static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String[] args) {
        testCollectingAndThen();
        testMapping();
    }

    /**
     * cr CollectingAndThen 先collectors.xxx，结果再function
     * Collectors里面返回值会被包一层 【Collector】 ，在collect方法里面把Collector中的【返回值】，解析数来
     * collector中的返回值，也可能会传入downStream进行后续的流操作
     */
    private static void testCollectingAndThen() {
        System.out.println("testCollectingAndThen");
        Optional.ofNullable(menu.stream().
                        collect(Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories),
                                a -> "The Average Calories " + "is->" + a)))
                .ifPresent(System.out::println);

        /**
         * cr 转变为List之后不希望再被修改，添加Collections::unmodifiableList
         * 下面再添加运行会报错。
         */
        List<Dish> list = menu.stream().filter(d -> d.getType().equals(Dish.Type.MEAT))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        list.add(new Dish("", false, 100, Dish.Type.OTHER));
        System.out.println(list);
    }




    // 后面这些也要了解在grouppingBy里面可能用到


    /**
     * cr Mapping 先map，然后Collector。 和上面的map(xxx.getxxx).collect() 一样的效果，idea建议map().collect()
     */
    private static void testMapping() {
        System.out.println("testMapping");
        Optional.of(menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(","))))
                .ifPresent(System.out::println);
    }





}

















