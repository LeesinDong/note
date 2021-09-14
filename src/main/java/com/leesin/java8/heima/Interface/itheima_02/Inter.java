package com.leesin.java8.heima.Interface.itheima_02;

public interface Inter {
    static void method1() {
    }

    static void method2() {
    }

    void show();

    default void method(){
        System.out.println("Inter 中的默认方法执行了");
    }

//    public static void test(){
//        System.out.println("Inter 中的静态方法执行了");
//    }

    static void test(){
        System.out.println("Inter 中的静态方法执行了");
    }

    void show1();

    void show2();
}
