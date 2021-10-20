package com.leesin.java8.wangwenjun.第15讲_completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    @org.junit.Test
    public void test() {
        // CompletableFuture<Void> com = CompletableFuture.supplyAsync(Lists::newArrayList)
        //         .thenApply(i -> Function.identity())
        //         .thenAccept(System.out::println)
        //         .whenComplete((i, e) -> {
        //             System.out.println(i);
        //             if (e != null) {
        //                 System.out.println(e.getMessage());
        //             }
        //         })
        //         .handle((i, t) -> {
        //             System.out.println(i);
        //             if (t != null) {
        //                 System.out.println(t.getMessage());
        //             }
        //             return i;
        //         }).exceptionally((t) -> {
        //             System.out.println(t.getMessage());
        //             return null;
        //         });

        Integer join = CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> i + 1))
                .join();
        System.out.println(join);

        System.out.println();

        Integer join1 = CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    return r1 + r2;
                }).join();
        System.out.println(join1);

        System.out.println();

        CompletableFuture.supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                }).join();

        CompletableFuture.supplyAsync(() -> 1)
                .runAfterBoth(CompletableFuture.supplyAsync(() -> 2),
                        () -> System.out.println("donw"));

        CompletableFuture.supplyAsync(() -> 1)
                .acceptEither(CompletableFuture.supplyAsync(() -> 2),
                        System.out::println);


        List<CompletableFuture<Integer>> collect = Arrays.asList(1, 2, 3).stream()
                .map(i -> CompletableFuture.supplyAsync(() -> i))
                .collect(Collectors.toList());
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(collect.toArray(new CompletableFuture[collect.size()]));
        voidCompletableFuture.join();

        List<CompletableFuture<Integer>> collect1 = Stream.of(1, 2, 3)
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    return i;
                }))
                .collect(Collectors.toList());

        List<Integer> collect2 = collect1.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
