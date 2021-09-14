package com.leesin.guava.第2讲_functions;

import com.google.common.base.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.leesin.java8.wangwenjun.第1讲_Functional.第1讲_FunctionalInterface.Apple;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
        List<String> collect = list.stream().map(Functions.toStringFunction()).collect(Collectors.toList());
        collect.forEach(System.out::println);

        Integer apply = Functions.forMap(ImmutableMap.of(1, 2, 3, 4)).apply(1);
        System.out.println(apply);

        Double apply1 = Functions.compose(new Function<Integer, Double>() {
            @Nullable
            @Override
            public Double apply(@Nullable Integer input) {
                return Double.valueOf(input);
            }
        }, new Function<String, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                return Integer.parseInt(input);
            }
        }).apply("2");
        System.out.println(apply1);

        assertThat(Functions.forPredicate((x) -> (int) x > 0).apply(1), equalTo(true));
        assertThat(Functions.constant("1").apply(2), equalTo("1"));
        assertThat(Functions.forSupplier(() -> 2).apply(1), equalTo(2));

        Suppliers.memoize(() -> new Apple()).get();

        Preconditions.checkArgument(Predicates.and((i) -> (int)i < 2, (i) -> (int)i > 0).apply(1));

        Preconditions.checkArgument(((Predicate<Integer>) i -> i > 0).and((i) -> i < 2).test(1));

        Preconditions.checkArgument(Predicates.in(Lists.newArrayList("1", "2")).test("1"));
        assertThat(Predicates.containsPattern("a").apply("a"), equalTo(true));

    }
}
