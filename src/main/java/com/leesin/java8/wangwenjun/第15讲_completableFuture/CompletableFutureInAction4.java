package com.leesin.java8.wangwenjun.第15讲_completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/13 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CompletableFutureInAction4 {

    public static void main(String[] args) throws InterruptedException  {

        // 单个
        /**
         * 【1】 supplyAsync 构建一个异步的任务，参数是supplier，有返回值
         */


        /**
         * 本质都一样，都是等待前一个任务完成，进行链式操作
         *
         * - 描述性的组装流水线，不会阻塞主线程
         * thenApply、handle ---- function，handle多了e
         * thenAccept、whenComplete ---- consumer，whenComplete多了e
         * thenRun ---- runnable 不关心返回的结果
         *
         * - 阻塞主线程
         * join、get ---- 直接获取结果不往下处理了 | join uncheck异常不用处理，get check异常，两者都可以抛出异常
         */

        /**
         * 【2】 thenApply
         */
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> System.out.println(v));
        /**
         * 【3】 handle、【4】whenComplete、【5】exceptionally【5】thenRun
         * handle、whenComplete
         * 二者的区别handle入参是biFunction，whenComplete入参是biConsumer；返回值handle是return的， whenComplete传入什么返回什么
         * 二者相同的：和thenApply一样， 只不过多了一个可以用于异常的处理
         * exceptionally 和 handle、whenComplete一样【三个都是只作用于前面的处理器，完成仅限于前面，异常也是，前面一个没有异常就接收不到】
         * thenRun没有入参，入参就是一个runnable
         * https://blog.csdn.net/winterking3/article/details/116477522
         *
         *  whenCompleteAsync，异步的等待处理结果；whenComplete会阻塞等待
         *  如果是方法后面加了Async表示异步执行,就是从ForkJoinPool.commonPool-worker线程池里里面重新选择线程,可能是同一个线程,
         *  可能不是同一个线程,如果没有加,就代表使用返回当前结果的线程执行…
         *  https://blog.csdn.net/qq_38366063/article/details/106947363
         */
        CompletableFuture.supplyAsync(() -> 1)
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                /**
                 * thenRun 没有入参，所有操作都做完再做一个操作，thenRunAsync异步的
                 * 这里没有入参只是打印了一个回车，可以用来最后完了之后做一个统计
                 */
                .thenRun(System.out::println);
        /**
         * 【6】 thenAccept
         * 和thenApply的区别 没有返回值
         */
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .thenAccept(System.out::println);









        // 多个组合completableFuture
        /**
         * thenCompose 第二个future操作第一个future完成时的结果
         * thenCombine、thenAcceptBoth 操作两个future结果，一个function 一个consumer
         */

        /**
         * 【7】 thenCompose 产生的结果交给另一个completableFuture处理
         */
        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);
        /**
         * 【8】thenCombine（第二个completableFuture，（第二个completableFuture的结果 、 第一个completableFuture的结果 的一个function））
         */
        CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);
        /**
         * 【9】thenAcceptBoth（第二个completableFuture，（第二个completableFuture的结果 、 第一个completableFuture的结果 的一个consumer））
         * 和上面的区别，这里是consumer
         */
        CompletableFuture.supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                }); // 后面还可以跟thenRun
        Thread.sleep(1000L);


        // 全部、任意完成
        /**
         * 【10】runAfterBoth   两个future都执行完了之后，执行runnable
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            return 2;
        }), () -> System.out.println("done"));
        /**
         * 【11】runAfterEither 和上面一样，只不过这里不能接受两个completableFuture的内容
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1略.get();
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1略.get();
        }), () -> System.out.println("done."));
        /**
         * 【12】applyToEither 其中有一个执行完成就执行，其中的这个执行的这个结果，通过thenApply进行后续的操作
         */
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("I am future 1");
                    return CompletableFutureInAction1略.get();
                }).applyToEither(CompletableFuture.supplyAsync(() -> {
                    System.out.println("I am future 2");
                    return CompletableFutureInAction1略.get();
                }), v -> v * 10)
                .thenAccept(System.out::println);
        /**
         * 【13】acceptEither 谁快就用谁，和上面一样，上面是function，这里是consumer
         */
        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1略.get();
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1略.get();
        }), System.out::println);


        /**
         * 【14】allOf全部执行完了之后，【15】anyOf任意一个执行完了之后
         */
        List<CompletableFuture<Double>> collect = Arrays.asList(1, 2, 3, 4)
                .stream()
                .map(i -> CompletableFuture.supplyAsync(CompletableFutureInAction1略::get))
                .collect(toList());

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));


        Thread.currentThread().join();
    }
}
