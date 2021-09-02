package com.leesin.java8.wangwenjun.第1讲_Functional.第3讲_四个特殊的Functional;

import com.leesin.java8.wangwenjun.第1讲_Functional.第1讲_FunctionalInterface.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * Created by wangwenjun on 2016/10/15.
 */
public class LambdaUsage {
    /**
     * 本质：Predicate、consumer、function、supplier等本质就是一个【需要参数的lambda表达式】，在调用处给【i -> xxx】这种具体实现，即Predicate
     * 1 【i -> xxx 】， 可以将 Predicate、consumer、function、supplier 当做参数封装到方法内, 然后在调用处再给具体实现, 也可以直接使用而不封装，直接 这种形式
     * 需要在lambda外面包一层上下有其他实现的就包成方法， 不需要的就直接用
     * 2 本质就是 通过predicate.test(a)、consumer.accept(a)、function.apply(a)、 supplier.get() 等[传入外部参数]进行调用
     */

    /**
     * 子interface extends 父interface ，只有两个合起来只有一个(父子一共一个)的时候，才是@functionalInterface
     */


    /**
     * 入参从别的地方（这里的前面的List提供，predicate、consumer只提供关于入参的运算（实际实现））
     */

    /**
     * predicate
     */
    private static List<Apple> filter(List<Apple> source, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple a : source) {
            if (predicate.test(a)) {
                result.add(a);
            }
        }
        return result;

    }

    /**
     * LongPredicate
     */
    private static List<Apple> filterByWeight(List<Apple> source, LongPredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple a : source) {
            if (predicate.test(a.getWeight())) {
                result.add(a);
            }
        }
        return result;
    }

    /**
     * BiPredicate
     */
    private static List<Apple> filterByBiPredicate(List<Apple> source, BiPredicate<String, Long> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple a : source) {
            if (predicate.test(a.getColor(), a.getWeight())) {
                result.add(a);
            }
        }
        return result;
    }

    /**
     * consumer
     */
    private static void simpleTestConsumer(List<Apple> source, Consumer<Apple> consumer) {
        for (Apple a : source) {
            consumer.accept(a);
        }
    }

    /**
     * biConsumer
     */
    private static void simpleBiConsumer(String c, List<Apple> source, BiConsumer<Apple, String> consumer) {
        for (Apple a : source) {
            consumer.accept(a, c);
        }
    }

    /**
     * function
     */
    private static String testFunction(Apple apple, Function<Apple, String> fun) {
        return fun.apply(apple);
    }

    /**
     * BiFunction
     */
    private static Apple testBiFunction(String color, long weight, BiFunction<String, Long, Apple> fun) {
        return fun.apply(color, weight);
    }

    /**
     * supplier
     */
    private static Apple createApple(Supplier<Apple> supplier) {
        return supplier.get();
    }


    public static void main(String[] args) {

        Runnable r1 = () -> System.out.println("Hello");

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        /**
         * 一样的效果
         */
        process(r1);
        process(r2);
        process(() -> System.out.println("Hello"));


        List<Apple> list = Arrays.asList(new Apple("green", 120), new Apple("red", 150));

        List<Apple> greenList = filter(list, (apple) -> apple.getColor().equals("green"));
        System.out.println(greenList);


        List<Apple> result = filterByWeight(list, w -> w > 100);
        System.out.println(result);

        List<Apple> result2 = filterByBiPredicate(list, (s, w) -> s.equals("green") && w > 100);
        System.out.println(result2);

        System.out.println("================");
        simpleTestConsumer(list, a -> System.out.println(a));

        System.out.println("================");
        simpleBiConsumer("XXX", list, (a, s) -> System.out.println(s + a.getColor() + ":Weight=>" + a.getWeight()));

        System.out.println("================");
        String result3 = testFunction(new Apple("yellow", 100), (a) -> a.toString());
        System.out.println(result3);

        /**
         * intFunction ==== 可以发现Function等，本质就是一个lambda
         */
        IntFunction<Double> f = i -> i * 100.0d;
        double result4 = f.apply(10);
        System.out.println("================");
        System.out.println(result4);

        System.out.println("================");
        Apple a = testBiFunction("Blue", 130, (s, w) -> new Apple(s, w));
        System.out.println(a);

        //method inference.
        Supplier<String> s = String::new;
        System.out.println(s.get().getClass());

        System.out.println("================");

        Apple a2 = createApple(() -> new Apple("Green", 100));
        System.out.println(a2);

        /**
         * 关于 lambda里面 操作的值 [默认都是final的]
         **/
        final int i = 0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // 内部类里面的i必须是final类型的
                // i++;

                /**
                 * 这里使用i，即使上面int i = 0, 上面默认也是加了final的,即 final int i = 0;
                 */
                System.out.println(i);
            }
        };
        // 如果上面 System.out.println(i); 使用了i，默认变成了final的，则即使上面int i = 0;这里也会报错，因为变成了final
        // i++;

        // 匿名内部类也是lambda表达式，这里同样不能++
        // Runnable r3 = () -> i++;
        Runnable r3 = () -> System.out.println(i);

        //=====================

        BiFunction<String, Integer, Integer> stringIntegerIntegerBiFunction = Integer::parseInt;
        list.sort(Comparator.comparing(Apple::getWeight));

        Function<String, Integer> stringIntegerFunction = String::length;

//        BiConsumer<PrintStream, String> test = System.out::

        System.out.println();


        BiConsumer<Test, String> say = Test::say;

        Consumer<String> c = System.out::println;

    }

    public static void useTest(BiConsumer<Test, String> con, List<String> list) {

    }

    private static void process(Runnable r) {
        r.run();
    }



    interface       Test {
        public void say(String s);
    }
}
