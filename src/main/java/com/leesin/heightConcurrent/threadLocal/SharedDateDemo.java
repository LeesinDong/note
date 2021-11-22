package com.leesin.heightConcurrent.threadLocal;

import org.junit.Test;

import java.util.Date;

/**
 * @author Leesin Dong
 * @since 2021/11/9
 */
public class SharedDateDemo {
    // 【1】共享基本数据类型支持 ，因为不是一个引用
    // 【2】Integer i = 0;
    // 就是状态 ，共享对象不支持，有并发问题，不是每个ThreadLocal一个副本
    Demo demo = new Demo();
    // private final ThreadLocal threadLocal =ThreadLocal.withInitial(()-> i);
    private final ThreadLocal threadLocal =ThreadLocal.withInitial(()-> demo);

                // demo.incr();
    @Test
    public void test() throws InterruptedException {
        for (int i1 = 0; i1 < 100; i1++) {
            new Thread(() -> {
                // int i = (int) threadLocal.get();
                // i++;
                // System.out.println(i);

                Demo demo = (Demo) threadLocal.get();
                demo.setI(demo.getI() + 1);
                System.out.println(demo.getI());
            }).start();
        }
        Thread.sleep(1000);
        // System.out.println(demo.getI());
        // System.out.println(i);
        // System.out.println(demo);
    }

    @Test
    public void testLocal() throws InterruptedException {
        for (int i1 = 0; i1 < 100; i1++) {
            new Thread(() -> {
                // 【3】局部变量是安全的
                Date date = new Date();
                threadLocal.set(date);
                int i = (int) threadLocal.get();
                System.out.println(i);
                i++;
                System.out.println(i);
                // Demo demo = (Demo) threadLocal.get();
            }).start();
        }
        Thread.sleep(1000);
        // System.out.println(i);
        // System.out.println(demo);
    }

}
