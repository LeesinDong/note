package com.leesin.java8.wangwenjun.第12讲_collector.rsgp;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/29 QQ:532500648
 * QQ交流群:286081824
 ***************************************/

import com.leesin.java8.wangwenjun.第5讲_stream.第5讲_stream介绍.Dish;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static com.leesin.java8.wangwenjun.第12讲_collector.mix.CollectorsAction1_$$$collectingAndThen$$$_mapping.menu;


public class CollectorsAction3_reducing_$$$summarizing_join$$$ {

    public static void main(String[] args) {

        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentiy();
        testReducingBinaryOperatorAndIdentiyAndFunction();
        testSummarizingDouble();
        testSummarizingLong();
        testSummarizingInt();
    }

    /**
     * cr counting collector里面有几个元素
     */
    private static void testCounting() {
        System.out.println("testCounting");
        Optional.of(menu.stream().collect(Collectors.counting())).ifPresent(System.out::println);
    }

    /**
     * maxBy 最大的xxx的实体 【和直接stream.max一样的效果】
     */
    private static void testMaxBy() {
        System.out.println("testMaxBy");
        menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }





    /**
     * minBy 最小的
     */
    private static void testMinBy() {
        System.out.println("testMinBy");
        menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    /**
     * cr Summing 求和 【等价于 mapToInt().sum()】
     */
    /**
     * summingDouble
     */
    private static void testSummingDouble() {
        System.out.println("testSummingDouble");
        Optional.of(menu.stream().collect(Collectors.summingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
        Optional.of(menu.stream().map(Dish::getCalories).mapToInt(Integer::intValue).sum())
                .ifPresent(System.out::println);
    }
    /**
     * summingLong
     */
    private static void testSummingLong() {
        System.out.println("testSummingLong");
        Optional.of(menu.stream().collect(Collectors.summingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
    /**
     * summingInt
     */
    private static void testSummingInt() {
        System.out.println("testSummingInt");
        Optional.of(menu.stream().collect(Collectors.summingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    /**
     * cr averaging 平均数
     */
    private static void testAveragingDouble() {
        System.out.println("testAveragingDouble");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testAveragingInt() {
        System.out.println("testAveragingInt");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testAveragingLong() {
        System.out.println("testAveragingLong");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(Dish::getCalories)))
                .ifPresent(System.out::println);

    }


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




    /**
     * cr reducing 【本质：这个和stream.reduce其实是一样的, idea推荐stream.reduce】
     */
    /**
     * reducing1
     */
    private static void testReducingBinaryOperator() {
        System.out.println("testReducingBinaryOperator");
        menu.stream().collect(
                Collectors.reducing(
                        BinaryOperator.maxBy(
                                Comparator.comparingInt(Dish::getCalories)
                        )
                )
        ).ifPresent(System.out::println);
    }
    /**
     * reducing2 有初始值identity
     */
    private static void testReducingBinaryOperatorAndIdentiy() {
        System.out.println("testReducingBinaryOperatorAndIdentiy");
        Integer result = menu.stream()
                .map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }
    /**
     * reducing3 初始值，map函数，reduce操作【本质：将reducing2中的map操作移到了reducing函数里面】
     */
    private static void testReducingBinaryOperatorAndIdentiyAndFunction() {
        System.out.println("testReducingBinaryOperatorAndIdentiyAndFunction");
        Integer result = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    public void test() {
        menu.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories))));
        menu.stream().map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
    }




    /**
     * cr summarizing 汇总 三个
     * 把count几个、sum总值、min、max、averaging都封装起来了
     */
    /**
     * summarizingDouble
     */
    private static void testSummarizingDouble() {
        System.out.println("testSummarizingDouble");
        Optional.of(menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
    /**
     * summarizingLong
     */
    private static void testSummarizingLong() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
    /**
     * summarizingInt
     */
    private static void testSummarizingInt() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
    /**
     * 略
     */
    // private static void testSummarizingIntIgnore() {
    //     System.out.println("testSummarizingInt");
    //     IntSummaryStatistics result = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    //     Optional.of(result).ifPresent(System.out::println);
    // }

}