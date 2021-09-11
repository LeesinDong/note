package com.leesin.java8.wangwenjun.第4讲_方法引用_函数推导_consumer例子;

import com.leesin.java8.wangwenjun.第1讲_Functional.第1讲_FunctionalInterface.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by wangwenjun on 2016/10/16.
 */
public class MethodReference {

    public static void main(String[] args) {

        // consumer
        Consumer<String> consumer = (s) -> System.out.println(s);
        useConsumer(consumer, "Hello Alex");



        /**
         * 总结：
         * 方法引用 = lambda，都需要传入参数
         * Functional = 方法引用 or lambda
         *
         * 方法引用 ----> 就是 lambda变体 ----> lambda中 【对象.方法(参数)】 中的 【对象、参数】用apply传入
         *
         *==========【lambda 和 optional 中能不能用的本质：lambda 和 optional流里面只能传一个对象，
         * 不论是123中的哪个，supplu中只能有一个参数，想用2则只能调用===对象对应的类中的空参方法====】==========
         */

        /**
         * 1 【类的静态方法】
         * Integer.parseInt(a)     类的方法
         */
        int value = Integer.parseInt("123");
        Function<String, Integer> f1 = (a) -> Integer.parseInt(a);
        Function<String, Integer> f = Integer::parseInt;
        Integer result = f.apply("123");
        System.out.println(result);

        /**
         * 2 3 本质一样，都是调用对象方法，只不过2是 对象所属的类::方法 并且需要传入【对象本身】作为参数
         * lambda中默认会传入类本身给方法引用，所以可以用类的方法引用
         */
        /**
         * 2 实例方法，即普通【类的实例方法】
         * String::charAt  ---> apply(stringValue, 2)             实例的类的方法
         */
        BiFunction<String, Integer, Character> f5 = (a, b) -> a.charAt(b);
        BiFunction<String, Integer, Character> f2 = String::charAt;
        Character c = f2.apply("hello", 2);
        System.out.println(c);
        // 工作中的用法
        Function<Apple, String> f52 = (a) -> a.getColor();
        Function<Apple, String> f22 = Apple::getColor;
        String c2 = f22.apply(new Apple("red", 12L));
        System.out.println(c2);

        /**
         * 3 【对象的实例方法】
         * stringValue::charAt ---> apply(2);
         */
        String stringValue = new String("Hello");
        BiFunction<String, Integer, Character> f6 = (a, b) -> a.charAt(b);
        Function<Integer, Character> f3 = stringValue::charAt;
        Character c22 = f3.apply(4);
        System.out.println(c22);


        /**
         * 4 【构造方法】
         */
        /**
         * 这个没有输入参数，所以结果啥也没有
         */
        Supplier<String> supplier = String::new;
        String s = supplier.get();
        System.out.println(s);
        /**
         * 普通类的构造函数
         */
        BiFunction<String, Long, Apple> appleFuntion = Apple::new;
        Apple apple = appleFuntion.apply("red", 100L);
        System.out.println(apple);
        /**
         * 没有三个参数的function，这里自定义
         */
        ThreeFunction<String, Long, String, ComplexApple> threeFunction = ComplexApple::new;
        ComplexApple complexApple = threeFunction.apply("Green", 123L, "FuShi");
        System.out.println(complexApple);









        /**
         * 下面是例子
         */
        /**
         * comparator方法引用
         */
        // 匿名内部类
        List<Apple> list1 = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110), new Apple("red", 123));
        list1.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        });
        // lambda
        List<Apple> list = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110), new Apple("red", 123));
        System.out.println(list);
        list.sort((a1, a2) -> a1.getColor().compareTo(a2.getColor()));
        System.out.println(list);
        list.stream().forEach(a -> System.out.println(a));
        System.out.println("==========================");
        list.stream().forEach(System.out::println);
        // 方法引用
        List<Apple> list2 = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110), new Apple("red", 123));
        System.out.println(list2);
        list2.sort(Comparator.comparing(Apple::getColor));
        System.out.println(list2);
        /**
         *  sout
         */
        useConsumer(s1 -> System.out.println(s1), "Hello Alex");
        useConsumer(System.out::println, "Hello Wangwenjun");
        /**
         * sum
         */
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Integer result2 = stream.reduce(0, Integer::sum);
        // 等效于下面
        Integer result1 = stream.reduce(0, (a, b) -> a + b);
    }

    private static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
        consumer.accept(t);
    }
}
