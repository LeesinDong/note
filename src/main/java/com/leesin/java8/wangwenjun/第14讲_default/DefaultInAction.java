package com.leesin.java8.wangwenjun.第14讲_default;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/5 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class DefaultInAction {


    /**
     * default 【接口.方法 直接调用，比如Function.identity()】 1 用于扩展，提供接口中默认实现，子类不需要都实现，2 多继承，实现多个接口，多个接口有default方法
     * static 【接口.方法 直接调用，比如Function.identity()】
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 最后执行哪个方法，取决于
         * 1 自己有重写，自己优先级最高 C
         * 2 自己的首个父亲 B，有实现，则他优先级最高，不论B上面有多少父亲
         * 3 自己的多个父亲没有关系，自己可能重写多个父亲的，则自己必须有具体实现，不然报错，具体实现：谁的都可以比如：A B
         */
        C c = new C();
        c.hello();
    }


    private interface A {

        default void hello() {
            System.out.println("==A.hello==");
        }
    }

    private interface B {

        default void hello() {
            System.out.println("==B.hello==");
        }
    }

    private static class C implements B, A {

        @Override
        public void hello() {
            B.super.hello();
        }
    }
}
