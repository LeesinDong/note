package com.leesin.java8.wangwenjun.第5讲_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/18.
 */
public class SimpleStream {
    public static void main(String[] args) {
        //have a dish list (menu)

        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        /**
         * 这里会报错，stream只能被操作一次，第一次操作完了，stream就已经关闭了。
         */
        /**
         * stream中spliterator()就是并行的原因，根据cpu将list进行分割的，做到真正的并行处理
         */
        Stream<Dish> stream = menu.stream();
        stream.forEach(System.out::println);
        stream.forEach(System.out::println);

        /**
         * stream 元素、 操作都是在里面做的，要干什么事把自己写的代码传进去
         * collection只包含元素，调用需要额外的一些方法（foreach），再加上自己写的代码
         */
        /**
         * 分为intermediate和terminal
         * intermediate会输入stream并返回一个stream，terminal会终端stream（foreach）
         */
        Stream<Dish> dishStream = Stream.of(new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        dishStream.forEach(System.out::println);

        System.out.println("=========================");

        List<String> result = menu.stream().filter(d -> {
                    System.out.println("filtering->" + d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                    System.out.println("map->" + d.getName());
                    return d.getName();
                })
                .limit(3).collect(toList());
        /**
         * 结果：
         * filtering ->
         * map->
         * filtering->
         * map->
         * filtering->
         * map->
         *
         * 是filter - map ，如此循环，而不是filter全部完了再全部map，由此可见是连续的
         */


        System.out.println("=======================");
        System.out.println(result);

/*        List<String> dishNamesByCollections = getDishNamesByCollections(menu);
        System.out.println(dishNamesByCollections);*/
      /*  List<String> dishNamesByStreams = getDishNamesByStream(menu);
        System.out.println(dishNamesByStreams);*/

    }

    /**
     * 通过stream达到效果
     *
     * @param menu menu
     * @return {@link List}
     */
    private static List<String> getDishNamesByStream(List<Dish> menu) {
        /**
         * 并行流
         * jconsole 发现会多几个fork join 线程，通过fork join实现并行流的
         */
        return menu.parallelStream().filter(d -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d.getCalories() < 400;
                }
                /**
                 * comparing(Dish::getCalories) 等效于 Comparator.comparing(Dish::getCalories)
                 * collect(toList()) 等效于 collect(Collectors.toList())
                 */
        ).sorted(comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
    }

    private static List<String> getDishNamesByCollections(List<Dish> menu) {
        List<Dish> lowCalories = new ArrayList<>();

        //filter and get calories less 400
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCalories.add(d);
            }
        }

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //sort
        /**
         * Collections 的 sort方法
         */
        Collections.sort(lowCalories, (d1, d2) -> Integer.compare(d1.getCalories(), d2.getCalories()));

        List<String> dishNameList = new ArrayList<>();
        for (Dish d : lowCalories) {
            dishNameList.add(d.getName());
        }
        return dishNameList;
    }
}
