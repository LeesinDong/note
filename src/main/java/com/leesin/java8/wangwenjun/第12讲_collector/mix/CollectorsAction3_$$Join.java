package com.leesin.java8.wangwenjun.第12讲_collector.mix;

import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.leesin.java8.wangwenjun.第12讲_collector.mix.CollectorsAction1_$$$collectingAndThen_averaging$$$_mapping_counting_summing_maxBy_minBy.menu;

public class CollectorsAction3_$$Join {
    /**
     * cr join
     */
    /**
     * join 对stream里面的值进行连接，【注意：collectors.joining()前面必须是charSequence，即String】
     */
    private static void testJoining() {
        System.out.println("testJoining");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining()))
                .ifPresent(System.out::println);
    }
    /**
     * join 带分隔符【每个单词中间加分隔符 1,2,3 只有中间有】
     */
    private static void testJoiningWithDelimiter() {
        System.out.println("testJoiningWithDelimiter");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(",")))
                .ifPresent(System.out::println);
    }
    /**
     * join 带分隔符、前缀、后缀【前缀、后缀是在加了分隔符的字符串的开始和结束, eg:Names[1,2,3]】
     */
    private static void testJoiningWithDelimiterAndPrefixAndSuffix() {
        System.out.println("testJoiningWithDelimiterAndPrefixAndSuffix");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(",", "Names[", "]")))
                .ifPresent(System.out::println);
    }
}
