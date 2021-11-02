package com.leesin.heightConcurrent.threadLocal;

import java.lang.ref.WeakReference;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/3/25 17:41
 * @version: ${VERSION}
 * @modified By:
 */
public class Demo {
     A a;

    private int i;

    public Demo() {

    }

    public void incr() {
        i++;
    }


    public Demo(A a) {
        this.a = a;
    }

    static class A {

    }

    public static void main(String[] args) {
        // A a= new A();
        // Demo demo = new Demo(a);
        // a = null;
        // System.out.println(demo.a);

        A a = new A();
        WeakReference demo = new WeakReference(a);
        System.out.println(demo.get());
        a = null;
        System.gc();//通知JVM的gc进行垃圾回收

        demo.clear();
        System.out.println(demo.get());
    }
}
