package com.leesin.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
class Test {
    public static void main(String[] args) {
        // ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);
        // List<Integer> collect = integers.stream().filter(i -> i > 4).collect(Collectors.toList());
        // log.error("haha");
        // Integer a = null;
        // // test(a);
        //
        // ArrayList<Integer> list = Lists.newArrayList();
        // list.add(1);
        // list.add(2);
        // list.add(3);
        // list.add(4);
        // long count = list.stream().filter(i -> i > 3).count();
        // List<Integer> collect = list.stream().collect(Collectors.toList());
        // System.out.println(count);
        // System.out.println(list);
        // System.out.println(collect);
        // @Bean
         ExecutorService THREAD_POOL = new ThreadPoolExecutor(6,
                6,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadFactoryBuilder().setNameFormat("reviewServiceExecutor-Thread-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        CompletableFuture<Object> f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("f");
            return null;
        });
        System.out.println("hahaha");

    }

    // public static void test(int a) {
    //     System.out.println(a);
    // }

    @Builder
    @Data
    public static class AClass {
        private String A;
        private String B;

        @Tolerate
        public AClass() {
        }
    }
}