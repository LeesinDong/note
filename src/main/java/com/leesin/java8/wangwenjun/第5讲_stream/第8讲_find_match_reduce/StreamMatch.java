package com.leesin.java8.wangwenjun.第5讲_stream.第8讲_find_match_reduce;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by wangwenjun on 2016/10/22.
 */
public class StreamMatch {

    public static void main(String[] args) {

        /**
         * cr: 有没有第一个想到match 相当于批量filter
         */

        /**
         * allMatch 里面的每个一个都满足allMath里面的条件
         */
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        boolean matched = stream.allMatch(i -> i > 10);
        System.out.println(matched);

        /**
         * anyMatch 任意一个满足
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        matched = stream.anyMatch(i -> i > 6);
        System.out.println(matched);

        /**
         * noneMatch 没有一个元素满足这个条件，都不符合这个条件
         */
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        matched = stream.noneMatch(i -> i < 0);
        System.out.println(matched);

    }
}
