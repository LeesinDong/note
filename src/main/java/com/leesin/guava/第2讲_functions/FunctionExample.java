package com.leesin.guava.第2讲_functions;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.leesin.java8.wangwenjun.第1讲_Functional.第1讲_FunctionalInterface.Apple;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.base.Predicates;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/9
 * @QQ: 532500648
 ***************************************/
@Slf4j
public class FunctionExample {
    public static void main(String[] args) throws IOException {
        // cr 本质：Functions、Suppliers、Predicates三个API   然后 apply()
        //   可以这么用：list.stream().map(Functions.toStringFunction())
        //   ，因为它们都继承了8的function、supplier、predicates，凡是8用到这三个的地方，都想想用三个能不能简化操作，声明式编程
        //   也可以直接apply



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
         * 1 toStringFunction 返回toString
         */
        // cr 重要
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
        assertThat(Functions.forPredicate((x) -> (int) x > 0).apply(1), equalTo(true));

        /**
         * 5 constant 不论传入的是什么，都返回构造的时候传入的value
         */
        assertThat(Functions.constant("1").apply(2), equalTo("1"));
        /**
         * 6 forSupplier 就是supplier
         */
        assertThat(Functions.forSupplier(() -> 2).apply(1), equalTo(2));
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

    public void testSupplier() {
        // cr guava中有Supplier（不是java8但是基本一样），同时Suppliers类对Function有一些方法
        /**
         * 1 compose 同上
         */
        /**
         * // cr 重要
         * 2 memoize 备忘录，就是单例模式，首次的时候初始化，初始化完了，后面的直接用，通过双重校验实现线程安全，里面对于是否序列化做了不同处理。
         */
        Suppliers.memoize(() -> new Apple()).get();
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
    }

    // cr guava中有Predicate（不是java8但是基本一样），同时Suppliers类对Function有一些方法
   public void testPredicate() {
       /**
        * // 略使用起来太繁琐了
        * 1 alwaysTrue
        */
       /**
        * // 略使用起来太繁琐了
        * 2 alwaysFalse
        */
       /**
        * // 略使用起来太繁琐了
        * 3 isNull
        */
       /**
        * // 略使用起来太繁琐了
        * 4 notnull
        */
       Preconditions.checkArgument(Predicates.notNull().test(1));
       // withNarrowedType ObjectPredicate强转成Predicate而已
       /**
        * 5 not 结果取反
        */
       /**
        * cr ======================================重要=======================================
        *  6 and再加入一个predicate，都是true才返回true，or 活的关系，一个为true则返回true
        *  guava: Preconditions.checkArgument(Predicates.and((i) -> (int)i < 2, (i) -> (int)i > 0).apply(1));
        *  8: Preconditions.checkArgument(((Predicate<Integer>) i -> i > 0).and((i) -> i < 2).test(1));
        *  =============================================================================
        * Java8中的Predicate没有and和or方法
        */
       com.google.common.base.Predicates.and((i) -> (int)i < 2, (i) -> (int)i > 0).apply(1);
       Preconditions.checkArgument(Predicates.and(Objects::isNull, Objects::isNull).test(1),
               "argument is error.");
       Preconditions.checkArgument(Predicates.or((x) -> (int)x > 1, (x) -> (int)x < 2).test(1),
               "df");

       /**
        * // cr 重要
        * 7 in 是否在里面
        */
       Preconditions.checkArgument(com.google.common.base.Predicates.in(Lists.newArrayList("1", "2")).test("1"));
       /**
        * // cr 重要
        * 8 containsPattern 等正则 contains
        *
        */
       assertThat(com.google.common.base.Predicates.containsPattern("a").apply("a"), equalTo(true));
       /**
        * 9 略 defensiveCopy 拷贝到list里面，浅拷贝
        */
   }
}
