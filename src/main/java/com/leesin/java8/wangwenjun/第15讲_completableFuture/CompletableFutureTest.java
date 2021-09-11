package com.leesin.java8.wangwenjun.第15讲_completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }, Executors.newFixedThreadPool(100))
                .thenRun(() -> System.out.println("a"));

        CompletableFuture<Object> a1 = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }, Executors.newFixedThreadPool(100))
                .thenApply(i -> {
                    System.out.println("1");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("a");
                });
        // CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(a, a1);
        // voidCompletableFuture.join();

        System.out.println("aaa");
    }
}
