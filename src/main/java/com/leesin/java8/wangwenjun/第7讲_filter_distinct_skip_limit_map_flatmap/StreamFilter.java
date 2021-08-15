package com.leesin.java8.wangwenjun.第7讲_filter_distinct_skip_limit_map_flatmap;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/20.
 */
public class StreamFilter {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7, 7, 1);

        /**
         * 过滤
         */
        List<Integer> result = list.stream().filter(i -> i % 2 == 0).collect(toList());
        System.out.println(result);

        /**
         * 去重
         */
        result = list.stream().distinct().collect(toList());
        System.out.println(result);

        /**
         * 截取前面5个元素，参数是跳过的个数
         */
        result = list.stream().limit(50).collect(toList());
        System.out.println(result);

        /**
         * 跳过前面5个元素，参数是跳过的个数
         */
        result = list.stream().skip(50).collect(toList());
        System.out.println(result);

        list.forEach(System.out::println);
        list.forEach(i -> System.out.println(i));
        list.forEach((Integer i) -> System.out.println(i));
        for (int i : list) {
            System.out.println(i);
        }
    }
}
