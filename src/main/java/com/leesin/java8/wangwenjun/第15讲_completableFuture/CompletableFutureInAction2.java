package com.leesin.java8.wangwenjun.第15讲_completableFuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/13 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CompletableFutureInAction2 {

    /**
     * 为什么要放到completableFuture里面线程池？
     */

    public static void main(String[] args)
            throws InterruptedException {
        AtomicBoolean finished = new AtomicBoolean(false);
        /**
         * cr main里面ExecutorService不是守护线程，默认，executor.shutdown才会退出
         */
        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

        /**
         * cr main里面new的completableFuture是守护线程，main结束后，它也死了。（因为fork join里面的线程是守护线程）
         *  可以通过设置finished标记保持main活着来等待completableFuture结束
         *  也可以，加入非守护线程的Executor【一般用这个】
         */
        CompletableFuture.supplyAsync(CompletableFutureInAction1略::get, executor)
                .whenComplete((v, t) -> {
                    Optional.of(v).ifPresent(System.out::println);
                    finished.set(true);
                });

        System.out.println("====i am no ---block----");
/*        while (!finished.get()) {
            Thread.sleep(1);
        }*/
    }
}
