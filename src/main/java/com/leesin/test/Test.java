package com.leesin.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
class Test {
    public static void main(String[] args) throws Exception {
        // throwa();
        CompletableFuture<Object> a = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }, Executors.newFixedThreadPool(100))
                .thenApply(i -> {
                    log.info("1");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("a");
                });

        CompletableFuture<Object> a1 = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }, Executors.newFixedThreadPool(100))
                .thenApply(i -> {
                    log.info("1");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("a");
                });
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(a, a1);
        voidCompletableFuture.join();

        // .handleAsync((i, e) -> {
                //     try {
                //         Thread.sleep(5000);
                //     } catch (InterruptedException ez) {
                //         ez.printStackTrace();
                //     }
                //     log.info("2");
                //     log.info("", e);
                //     return 1;
                // });
                // .whenComplete((i, e) -> {
                //     try {
                //         Thread.sleep(5000);
                //     } catch (InterruptedException ez) {
                //         ez.printStackTrace();
                //     }
                //     log.info("3");
                //     log.info("", e);
                // });
                // .thenRun(() -> {
                //     try {
                //         Thread.sleep(5000);
                //     } catch (InterruptedException ez) {
                //         ez.printStackTrace();
                //     }
                //     log.info("4");
                //     throw new RuntimeException("b");
                // });
                // .exceptionally((e) -> {
                //     try {
                //         Thread.sleep(5000);
                //     } catch (InterruptedException ez) {
                //         ez.printStackTrace();
                //     }
                //     log.info("", e);
                //     return null;
                // });
                // .thenAccept(i -> {
                //     log.info("5");
                // })
                // .exceptionally((e) -> {
                //     log.info("-", e);
                //     return null;
                // });

        System.out.println("aaa");
    }

    public static void throwa() {
        // throw new RuntimeException();
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        try {
            inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}