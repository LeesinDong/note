package com.leesin.java8.wangwenjun.第13讲_parallel.slightly;

import java.util.concurrent.ForkJoinPool;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/2 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class ForkJoinPoolTest {

    private static int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    /**
     * forkJoin分而治之的用法
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("result=> " + calc());

        /**
         * 两种方式的区别：compute方法一个有返回值一个没有
         */
        AccumulatorRecursiveTask task = new AccumulatorRecursiveTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(task);
        System.out.println("AccumulatorRecursiveTask >>" + result);

        AccumulatorRecursiveAction action = new AccumulatorRecursiveAction(0, data.length, data);
        forkJoinPool.invoke(action);
        System.out.println("AccumulatorRecursiveAction >>" + AccumulatorRecursiveAction.AccumulatorHelper.getResult());
    }


    private static int calc() {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i];
        }
        return result;
    }

}