package com.leesin.java8.wangwenjun.第5讲_stream.第9讲_numberic_streams;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 数字类型的stream
 * Created by wangwenjun on 2016/10/22.
 */
public class NumericStream {

    public static void main(String[] args) {
        /**
         * cr 本质：拆箱(节省内存)、装箱（复杂操作）
         * mapToInt、box.map | maoToObject
         */

        /**
         * 1 拆箱
         * 好处：节省内存，普通的stream返回里面是Integer，int类型占用的内存更小（4byte，32bit），所以希望int类型的Stream
         * 本质：就是mapToInt方法，返回的是IntStream，IntStream后续操作都是Intxxx的function
         * 纯数字的时候用IntStream操作节省内存
         */
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        IntStream intStream = stream.mapToInt(i -> i.intValue());
        // 这里filter里面的是IntPredicate，而不是普通的Predicate
        int result = intStream.filter(i -> i > 3).sum();
        System.out.println(result);


        /**
         * 2 装箱
         * 好处：inStream Map不能返回数组类型，有的需求不满足，cr Object类型的Map等函数功能更丰富
         * 本质：cr boxed().map || mapToObject
         */
        int a = 9;
        //1..1000
        //result int[a,b,c];
        /**
         * .boxed().map
         */
        /**
         * 是否close说的是右边，左边一定是闭的
         * rangeClose []
         * range [)
         */
        IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .boxed()
                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                .forEach(r -> System.out.println("a=" + r[0] + ",b=" + r[1] + ",c=" + r[2]));
        System.out.println("=======================");
        /**
         * mapToObject
         */
        IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                .forEach(r -> System.out.println("a=" + r[0] + ",b=" + r[1] + ",c=" + r[2]));
    }
}
