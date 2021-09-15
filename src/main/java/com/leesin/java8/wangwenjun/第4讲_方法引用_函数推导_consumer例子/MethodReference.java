package com.leesin.java8.wangwenjun.第4讲_方法引用_函数推导_consumer例子;

import com.google.common.collect.Lists;
import com.leesin.java8.wangwenjun.第1讲_Functional.第1讲_FunctionalInterface.Apple;

import java.util.*;
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
         * cr 方法引用 ----> Functional---------> 就是 lambda变体 ---->
         *  lambda中 【对象.方法(参数)】 中的 【对象、参数】用apply传入，stream中省略了apply过程
         *
         *==========【lambda 和 optional 中能不能用的本质：cr lambda 和 optional流里面只能传一个对象，
         * 不论是 下面123中的哪个，supply中只能有一个参数，想用2则只能调用===cr 对象对应的类中的空参方法====】==========
         */
        List<Apple> appleList = Lists.newArrayList(new Apple("abc", 123),
                new Apple("Green", 110),
                new Apple("red", 123));
        // cr stream|optional 方法引用本质：stream只传一个对象，故只支持以下格式：参数 <= 1 | 0 (当前可以支持的参数)
        //  1 静态方法(stream)、静态方法()-supplier || 构造方法(stream)、构造方法()-supplier
        //  2 stream类::对象方法(空参) --- 调用stream里面对象的方法,【stream里面的东西会首先尝试当做方法的参数而不是对象类型，所以会报错】
        //  3 其他对象::对象方法(stream)、其他对象::对象方法()-supplier

        // cr ========================重要===============================
        //  本质再总结
        //  ********stream optional中的操作都尽量用方法引用想 （stream对象方法 || guava静态方法）*******
        //  根据stream或optional提供的东西来判定走哪个方法
        //  本质两种：
        //  1 stream里面每个对象的空参方法 ********stream中的操作都尽量往这里想*******    2 不支持supplier
        //  2 其他类的方法 1 3 --- 单参(stream传) 或 空参(supplier)
        //
        //  cr 也支持多个参数，比如biFunction，比如reduce方法，这里符合13
        //   =======================================================
        ArrayList<Object> copyList = Lists.newArrayList();
        // 1
        appleList.forEach(System.out::print);
        Optional.ofNullable(1D).orElseGet(Math::random);
        // 2
        appleList.forEach(Apple::getColor);
        // error：只能空参方法，非空参，默认认为流中是传入参数的静态方法，而不是对象的类型，所以提示没有static方法
        // appleList.forEach(Apple::setColor);
        // 3
        appleList.forEach(copyList::add);
        // 4 本质还是2
        ArrayList<String> colorList = Lists.newArrayList("red");
        colorList.forEach(Apple::new);
        Optional.ofNullable(new Apple("Green", 110)).orElseGet(Apple::new);

        appleList.stream().map(Apple::getColor);
        appleList.stream().map(copyList::add);
        appleList.stream().forEach(System.out::println);
        colorList.stream().forEach(Apple::new);






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
         * 4 【构造方法】 本质还是2
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
        List<Apple> list1 = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110),
                new Apple("red", 123));
        list1.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        });
        // lambda
        List<Apple> list = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110),
                new Apple("red", 123));
        System.out.println(list);
        list.sort((a1, a2) -> a1.getColor().compareTo(a2.getColor()));
        System.out.println(list);
        list.stream().forEach(a -> System.out.println(a));
        System.out.println("==========================");
        list.stream().forEach(System.out::println);
        // 方法引用
        List<Apple> list2 = Arrays.asList(new Apple("abc", 123), new Apple("Green", 110),
                new Apple("red", 123));
        System.out.println(list2);
        // cr Comparator.comparing
        list2.sort(Comparator.comparing(Apple::getColor));
        list2.sort(Comparator.comparing((app) -> app.getColor()));
        list2.stream().sorted(Comparator.comparing(Apple::getColor));


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
