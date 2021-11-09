package com.leesin.heightConcurrent.threadLocal;

import org.junit.Test;

/**
 * @author Leesin Dong
 * @since 2021/11/9
 */
public class SharedDateDemo {
    // 共享基本数据类型支持
    Integer i = 0;
    // 就是对象字段
    // 共享对象不支持，有并发问题，不是每个ThreadLocal一个副本
    Demo demo = new Demo();
    private final ThreadLocal threadLocal =ThreadLocal.withInitial(()-> i);
    // private final ThreadLocal threadLocal =ThreadLocal.withInitial(()-> demo);

    @Test
    public void test() throws InterruptedException {
        for (int i1 = 0; i1 < 100; i1++) {
            new Thread(() -> {
                int i = (int) threadLocal.get();
                System.out.println(i);
                i++;
                System.out.println(i);
                // Demo demo = (Demo) threadLocal.get();
                // demo.incr();
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(i);
        // System.out.println(demo);
    }
}
