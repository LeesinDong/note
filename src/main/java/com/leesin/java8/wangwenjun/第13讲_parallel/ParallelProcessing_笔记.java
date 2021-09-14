package com.leesin.java8.wangwenjun.第13讲_parallel;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/30 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class ParallelProcessing_笔记 {

    public static void main(String[] args) {
        // 16ms
        System.out.println("The best process time(normalAdd)=>" + measureSumPerformance(ParallelProcessing_笔记::normalAdd, 100_000_000) + " MS");
        //  270ms
        System.out.println("The best process time(iterateStream)=>" + measureSumPerformance(ParallelProcessing_笔记::iterateStream, 10_000_000) + " MS");

        // 1445ms
        System.out.println("The best process time(parallelStream)=>" + measureSumPerformance(ParallelProcessing_笔记::parallelStream, 10_000_000) + " MS");
        // 494ms (手动拆箱，避免上面每次需要拆箱导致消耗性能)
        System.out.println("The best process time(parallelStream2)=>" + measureSumPerformance(ParallelProcessing_笔记::parallelStream2, 10_000_000) + " MS");
        // 7ms LongStream.rangeClosed
        System.out.println("The best process time(parallelStream3)=>" + measureSumPerformance(ParallelProcessing_笔记::parallelStream3, 100_000_000) + " MS");

        /**
         * 【不是所有的数据源都适合用并行，有的用了反而适得其反，比如上面例子中的Stream.iterate，有的用了会很快比如LongStream.rangeClosed，比普通的快了两倍】
         * 【根据产生stream的数据源来判断是否用并行】
         * 【Excellent 卓越的，特别适合；Good 良好的，比较适合；Poor 可怜的，非常不适合】
         *
         * cr Source               Decomposability
         *
         * cr ArrayList            Excellent
         *    LinkedList           Poor
         *
         * cr IntStream.range      Excellent
         *    Stream.iterate       Poor
         *
         * cr HashSet              Good
         *    TreeSet              Good
         */

        /**
         * （略）
         * 有一个forjJoin的参数可以设置cpu，可以不断自己调整，达到最优的效果，一般用默认的就行，不同机器还要再设置
         */
    }

    private static long measureSumPerformance(Function<Long, Long> adder, long limit) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long startTimestamp = System.currentTimeMillis();
            long result = adder.apply(limit);
            long duration = System.currentTimeMillis() - startTimestamp;
//            System.out.println("The result of sum=>" + result);
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }


    private static long iterateStream(long limit) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream(long limit) {
        return Stream.iterate(1L, i -> i + 1).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream2(long limit) {
        return Stream.iterate(1L, i -> i + 1).mapToLong(Long::longValue).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream3(long limit) {
        return LongStream.rangeClosed(1, limit).parallel().reduce(0L, Long::sum);
    }

    private static long normalAdd(long limit) {
        long result = 0L;
        for (long i = 1L; i < limit; i++) {
            result += i;
        }
        return result;
    }
}