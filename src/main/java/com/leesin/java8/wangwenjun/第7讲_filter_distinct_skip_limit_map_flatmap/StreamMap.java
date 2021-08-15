package com.leesin.java8.wangwenjun.第7讲_filter_distinct_skip_limit_map_flatmap;

import com.leesin.java8.wangwenjun.第5讲_stream.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/20.
 */
public class StreamMap {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7, 7, 1);

        /**
         * map
         */
        // 数字
        List<Integer> result = list.stream().map(i -> i * 2).collect(toList());
        System.out.println(result);
        // 对象
        listDish().stream().map(d -> d.getName()).forEach(System.out::println);
        List<String> dishes = listDish().stream().map(d -> d.getName()).collect(toList());
        System.out.println(dishes);

        /**
         * flatMap
         */
        //flatmap flat (扁平化)
        String[] words = {"Hello", "World"};
        //{h,e,l,l,o},{W,o,r,l,d}
        Stream<String[]> stream = Arrays.stream(words).map(w -> w.split(""));
        //H,e,l,l,o,W,o,r,l,d
        /**
         * 入参是 泛型为：【数组 或 List】 的Stream，必须是Stream<T> 中的T是数组 或 List，即双层数组结构
         * 参数是 Arrays::stream 或 Collection::stream
         */
        Stream<String> stringStream = stream.flatMap(Arrays::stream);
        stringStream.distinct().forEach(System.out::println);

    }


    private static List<Dish> listDish() {
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
        return menu;
    }
}
