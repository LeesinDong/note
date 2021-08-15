package com.leesin.java8.wangwenjun.第1讲_FunctionalInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangwenjun on 2016/10/12.
 */
public class FilterApple {


    /**
     * 只有一个抽象方法的接口可以用Lambda替代匿名内部类，可以用@FunctionalInterface标注，不标注也行，但是要一个方法，多个方法@FunctionalInterface会报错
     * 可以包含default 和 static方法，即使存在，也是认为是FunctionalInterface的，但是普通方法就不行
     */
    /**
     * Lambda是Functional的具体实现，Functional相当于只是一个接口、架子；Predicate是特殊的Functional
     * 本质：===========【FunctionalInterface = lambda（唯一方法的具体实现)】 = 方法引用    即 接口方法 = 接口方法的具体实现=======
     * 方法引用 == Lambda
     */
    @FunctionalInterface
    public interface AppleFilter {

        boolean filter(Apple apple);

        /**
         * default方法
         */
        default void print(String var) {
            System.out.println(var);
        }

        /**
         * static方法
         * @param var
         */
        static void print1(String var) {
            System.out.println(var);
        }

    }

    /**
     * 这里 AppleFilter 本质和 predicate等是一样的
     */
    public static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }

    public static class GreenAnd160WeightFilter implements AppleFilter {

        /**
         * 具体实现要指定类型，比如List<Apple> = new ArrayList<Apple>
         * 这里lambda本质也是functional的具体实现，所以这里的Apple不能再是泛型，【如果希望用泛型，可以在已经有泛型的方法里面调用lambda，这样lambda的入参可以用外面包着这层的类型】
         */
        @Override
        public boolean filter(Apple apple) {
            return ("green".equals(apple.getColor()) && apple.getWeight() >= 160);
        }
    }

    public static class YellowLess150WeightFilter implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return ("yellow".equals(apple.getColor()) && apple.getWeight() < 150);
        }
    }

    public static List<Apple> findGreenApple(List<Apple> apples) {

        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                list.add(apple);
            }
        }

        return list;
    }

    public static List<Apple> findApple(List<Apple> apples, String color) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {
                list.add(apple);
            }
        }

        return list;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Apple> list = Arrays.asList(new Apple("green", 150), new Apple("yellow", 120), new Apple("green", 170));
//        List<Apple> greenApples = findGreenApple(list);
//        assert greenApples.size() == 2;

       /* List<Apple> greenApples = findApple(list, "green");
        System.out.println(greenApples);

        List<Apple> redApples = findApple(list, "red");
        System.out.println(redApples);*/
/*
        List<Apple> result = findApple(list, new GreenAnd160WeightFilter());
        System.out.println(result);

        List<Apple> yellowList = findApple(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor());
            }
        });
        System.out.println(yellowList);*/

        /**
         * Functional接口的唯一方法的 参数(这里是形参) + 接口具体实现
         */
        List<Apple> lambdaResult = findApple(list, apple -> "green".equals(apple.getColor()));

        System.out.println(lambdaResult);

        /**
         * Runnable 也是 @FunctionalInterface 接口
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();


        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();


        Thread.currentThread().join();
    }


}
