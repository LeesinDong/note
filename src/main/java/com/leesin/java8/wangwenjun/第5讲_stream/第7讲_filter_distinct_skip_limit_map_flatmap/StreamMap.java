package com.leesin.java8.wangwenjun.第5讲_stream.第7讲_filter_distinct_skip_limit_map_flatmap;

import com.google.common.collect.Lists;
import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;
import org.elasticsearch.common.Strings;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/20.
 */
public class StreamMap {

    public static void main(String[] args) {

        // cr 全部
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7, 7, 1);

        /**
         * map
         */
        // 数字
        List<Integer> result = list.stream().map(i -> i * 2).collect(toList());
        System.out.println(result);
        // 对象
        listDish().stream().map(Dish::getName).forEach(System.out::println);
        List<String> dishes = listDish().stream().map(Dish::getName).collect(toList());
        System.out.println(dishes);

        /**
         * flatMap
         */
        //flatmap flat (扁平化)
        String[] words = {"Hello", "World"};
        //{h,e,l,l,o},{W,o,r,l,d}
        /**
         * 入参是 泛型为：【数组 或 List】 的Stream，必须是Stream<T> 中的T是数组 或 List，即双层数组结构
         * 参数是 Arrays::stream 或 Collection::stream
         */
        Arrays.stream(words).map(Strings::splitStringByCommaToArray).flatMap(Arrays::stream).forEach(System.out::print);
        Arrays.stream(words).map(Lists::charactersOf).flatMap(Collection::stream).forEach(System.out::print);
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
