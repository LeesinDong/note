package com.leesin.java8.heima.stream;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class max {
    public static void main(String[] args) {
        ArrayList<Integer> list = Lists.newArrayList(1, 3, 2, 4, 6);


        Apple apple1 = new Apple(1);
        Apple apple2 = new Apple(2);
        Apple apple3 = new Apple(3);
        Apple apple4 = new Apple(3);
        ArrayList<Apple> apples = Lists.newArrayList(apple1, apple2, apple3, apple4);
        Map<Integer, Set<Apple>> collect = apples.stream().collect(Collectors.groupingBy(Apple::getValue, Collectors.toSet()));
        System.out.println(collect);
        // list = (ArrayList<Integer>) list.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        // list = (ArrayList<Integer>) list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        // list.stream().min(Comparator.comparing(Integer::intValue)).ifPresent(System.out::println);
        // list.stream().max(Comparator.comparing(Integer::intValue)).ifPresent(System.out::println);
        // list.stream().max(Comparator.comparing(Integer::intValue))
        // list.sort(Comparator.comparing(Integer::intValue));
        // list.sort(Comparator.comparing(Integer::intValue, Comparator.comparing(max::sort1).reversed()));
        // apples.sort(Comparator.comparing(Apple::getValue, Comparator.comparing(max::sort1).reversed()));
        // System.out.println( apples);
       // Optional.ofNullable(list).filter(CollectionUtils::isNotEmpty).orElseThrow(() -> {
       //     System.out.println("111");
       //     log.error("1234");
       //     return new RuntimeException("抛出异常了");
       // });

        Apple a = new Apple(1);
        // 会覆盖
        Apple b = new Apple();
        BeanUtils.copyProperties(a, b);
        // System.out.println(b);
        // return = continue
        list.forEach(i -> {
            if (i == 3) {
                return;
            }

            System.out.println(i);
        });
    }

    public static Integer sort1(Integer value) {
        return -value;
    }

    @Data
    @NoArgsConstructor
    static class Apple {
        Integer value;

        public Apple(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
