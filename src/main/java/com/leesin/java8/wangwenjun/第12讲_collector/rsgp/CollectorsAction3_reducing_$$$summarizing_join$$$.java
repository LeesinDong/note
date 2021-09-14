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

import static com.leesin.java8.wangwenjun.第12讲_collector.mix.CollectorsAction1_$$$collectingAndThen_averaging$$$_mapping_counting_summing_maxBy_minBy.menu;


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