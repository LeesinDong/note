package com.leesin.heightConcurrent.threadLocal;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/3/24 22:17
 * @version: ${VERSION}
 * @modified By:
 */
public class ThreadLocalDemo {

    // static ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
    //     @Override
    //     protected Integer initialValue() {
    //         return 0;
    //     }
    // };
    static ThreadLocal<Integer> num = ThreadLocal.withInitial(() -> 0);
    static ThreadLocal<String> strLocal = ThreadLocal.withInitial(() -> "Hello");

    //每个线程的变量副本是如何存储的？
    //ThreadLocal是什么时候初始化的

    public static void main(String[] args) {
        //每个线程拿到的num值是不确定？
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Integer localNum = num.get();//获得ThreadLocal中的初始值
                localNum = localNum + 5;
                num.set(localNum);//何止修改以后的值

                String s = strLocal.get();
                strLocal.set(s+" world");//何止修改以后的值
                System.out.println(Thread.currentThread().getName()+":"+num.get()+" -》 " +strLocal.get() );
                // 在每个线程定义的最后
                // 删除当前线程的ThreadLocal key 为null 的value
                strLocal.remove();
            },"Thread-"+i);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

}
//每个线程都拿到一个副本，线程范围的共享，线程级别隔离的
