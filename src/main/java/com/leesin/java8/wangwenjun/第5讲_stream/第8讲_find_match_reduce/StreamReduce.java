package com.leesin.java8.wangwenjun.第5讲_stream.第8讲_find_match_reduce;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by wangwenjun on 2016/10/22.
 */
public class StreamReduce {

    public static void main(String[] args) {

        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        /**
         * cr 1 求和 【有初始值返回的是结果】【初始值 + list中所有的】
         */
        Integer result = stream.reduce(0, Integer::sum);
        // Integer result = stream.reduce(2, Integer::sum);
        // 等效于下面
        // Integer result1 = stream.reduce(1, (a, b) -> a + b);
        System.out.println(result);

        /**
         * cr 1 求和 【没有初始值，返回的是Optional】【只会把list中所有的相加】
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce(Integer::sum).ifPresent(System.out::println);



        /**
         * cr 2 最大值
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce(Integer::max).ifPresent(System.out::println);
        // 等效于这里
        stream.reduce((i, j) -> i > j ? i : j).ifPresent(System.out::println);





        /**
         * cr 3 最小值
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce(Integer::min).ifPresent(System.out::println);
        // 等效于
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.reduce((i, j) -> i > j ? j : i).ifPresent(System.out::println);



        /**
         * cr 4 偶数元素相乘
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        int result2 = stream.filter(i -> i % 2 == 0).reduce(1, (i, j) -> i * j);
        Optional.of(result2).ifPresent(System.out::println);

    }

    public void test() {
        int reduce1 = IntStream.rangeClosed(1, 7).reduce(0, Integer::sum);
        IntStream.rangeClosed(1, 7).boxed().reduce(Integer::sum);
        IntStream.rangeClosed(1, 7).boxed().reduce(Integer::max);
        IntStream.rangeClosed(1, 7).boxed().reduce(Integer::min);
        IntStream.rangeClosed(1, 7).boxed().filter(i -> i % 2 == 0).reduce(Integer::sum);
    }
}
