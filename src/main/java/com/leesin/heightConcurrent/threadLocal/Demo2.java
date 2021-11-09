package com.leesin.heightConcurrent.threadLocal;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/3/25 17:40
 * @version: ${VERSION}
 * @modified By:
 */

public class Demo2 {
    //共享 & 可变数据   不用static，是一个共享 可变 的状态 即可
    static Demo demo = new Demo();

    // 返回同一个对象引用 demo
    static ThreadLocal<Demo> num = ThreadLocal.withInitial(() -> demo);
    static ThreadLocal<String> strLocal = ThreadLocal.withInitial(() -> "Hello");

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(Demo2::run, "Thread-" + i);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    // 每个线程里面 操作ThreadLocal    一个线程模板 对应 操作

    // 多个ThreadLocal对象， 一个运行的线程对应一个ThreadLocal
    private static void run() {
        Demo demo1 = num.get();
        //这种方式还会起到隔离作用哦吗？
        //ThreadLocal是否会遇到跨线程的数据安全性问题

        //get方法中有一个setInitialValue -> initialValue，如果返回的是同一个实例的话，
        // 多个线程 修改同一个实例还是改变的一个共享的数据，所以是无法保证原子性的
        demo1.incr();
    }
}
