package com.leesin.java8.wangwenjun.第5讲_stream.第7讲_filter_distinct_skip_limit_map_flatmap;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/20.
 */
public class StreamFilter {

    public static void main(String[] args) {
        // cr 全部
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 7, 7, 1);

        /**
         * fileter 过滤
         */
        List<Integer> result = list.stream().filter(i -> i % 2 == 0).collect(toList());
        System.out.println(result);

        /**
         * distinct 去重
         */
        result = list.stream().distinct().collect(toList());
        System.out.println(result);
        // 去重汇总
        long count = list.stream().distinct().count();
        // 普通求和
        int sum = list.stream().mapToInt(i -> i * 2).sum();

        /**
         * limit 从第一个开始，截取前面5个元素，参数是跳过的个数
         */
        result = list.stream().limit(5).collect(toList());
        System.out.println("limit" + result);

        /**
         * skip 从第一个开始，跳过前面5个元素，参数是跳过的个数
         */
        result = list.stream().skip(5).collect(toList());
        System.out.println("skip" + result);

        list.forEach(System.out::println);
        list.forEach(System.out::println);
        list.forEach(System.out::print);
        for (int i : list) {
            System.out.println(i);
        }
    }
}
