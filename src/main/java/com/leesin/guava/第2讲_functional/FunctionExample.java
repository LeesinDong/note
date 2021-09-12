package com.leesin.guava.第2讲_functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.base.Predicates;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/9
 * @QQ: 532500648
 ***************************************/
@Slf4j
public class FunctionExample {
    public static void main(String[] args) throws IOException {
        // cr 本质：Functions、Suppliers、Predicates三个API   然后 apply()


        // cr guava中有Function（不是java8但是基本一样），同时Functions类对Function有一些方法
        Function<String, Integer> function = new Function<String, Integer>() {

            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                Preconditions.checkNotNull(input, "The input Stream should not be null.");
                return input.length();
            }
        };

        // 输出input的长度
        System.out.println(function.apply("Hello"));
        // 这里是策略模式的扩展，输出input的二倍，略
        process("Hello", new Handler.LengthDoubleHandler());
        /**
         * 1 toStringFunction 返回toString 没用
         */
        System.out.println(Functions.toStringFunction().apply(new ServerSocket(8888)));

        /**
         * 2 forMap -> FunctionForMapNoDefault 返回map里面的某个key 没有则抛异常
         * forMap -> ForMapWithDefault 返回map里面的某个key 没有则返回默认值
         */
        String apply = Functions.forMap(ImmutableMap.of("1", "2")).apply("1");
        String apply1 = Functions.forMap(ImmutableMap.of("0", "2"), "0").apply("1");
        log.info("apply={}, apply1={}", apply, apply1);

        /**
         * 3 compose，组合 (function2<b, c>, function2<a, b>) 先运行function2 a变成b，然后运行function1 b变成c。
         */
        Function<String, Double> compose = Functions.compose(new Function<Integer, Double>() {
            @Nullable
            @Override
            public Double apply(@Nullable Integer input) {
                return input * 1.0D;

            }
        }, new Function<String, Integer>() {

            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                return input.length();
            }
        });
        System.out.println(compose.apply("Hello"));

        /**
         * 4 forPredicate 就是Predicate
         */

        /**
         * 5 constant 不论传入的是什么，都返回构造的时候传入的value
         */

        /**
         * 6 forSupplier 就是supplier
         */

    }

    interface Handler<IN, OUT> {

        OUT handle(IN input);


        class LengthDoubleHandler implements Handler<String, Integer> {

            @Override
            public Integer handle(String input) {
                return input.length() * 2;
            }
        }
    }

    private static void process(String text, Handler<String, Integer> handler) {
        System.out.println(handler.handle(text));
    }

    // cr guava中有Supplier（不是java8但是基本一样），同时Suppliers类对Function有一些方法
    /**
     * 1 compose 同上
     */
    /**
     * 2 memoize 备忘录，就是单例模式，首次的时候初始化，初始化完了，后面的直接用，通过双重校验实现线程安全，里面对于是否序列化做了不同处理。
     */
    /**
     * 3 memoizeWithExpiration 备忘录加上超时功能
     */
    /**
     * 4 ofInstance 放进去什么就返回什么
     */
    /**
     * 5 synchronizedSupplier 同ofInstance，加了一个锁而已
     */
    /**
     * 6 supplierFunction 传入supplier，返回 supplier的返回值
     */

    // cr guava中有Predicate（不是java8但是基本一样），同时Suppliers类对Function有一些方法
    /**
     * 1 alwaysTrue
     */
    /**
     * 2 alwaysFalse
     */
    /**
     * 3 isNull
     */
    /**
     * 4 notnull
     */
    // withNarrowedType ObjectPredicate强转成Predicate而已
    /**
     * 5 not 结果取反
     */
    /**
     * cr 6 and再加入一个predicate，都是true才返回true，or 活的关系，一个为true则返回true
     * Java8中的Predicate也有and和or方法
     */
    public static void test() {
        Preconditions.checkArgument(Predicates.and(Objects::isNull, Objects::isNull).test(1),
                "argument is error.");
        Preconditions.checkArgument(Predicates.or((x) -> (int)x > 1, (x) -> (int)x < 2).test(1),
                "df");
    }

    /**
     * 7 in 是否在里面
     */
    /**
     * 8 containsPattern 等正则 contains
     */
    /**
     * 9 defensiveCopy 拷贝到list里面，浅拷贝
     */
}
