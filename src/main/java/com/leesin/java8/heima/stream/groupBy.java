package com.leesin.java8.heima.stream;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://blog.csdn.net/u012881904/article/details/51325044
 * 和mysql group by一样的：多条件的笛卡尔积取出其中没有的数据行（其中的b甲就没有）
 */
public class groupBy {
    public static void main(String[] args) {
        Apple build1 = Apple.builder().name("a1").level("甲1").age(11).build();
        Apple build2 = Apple.builder().name("a1").level("甲2").age(12).build();
        Apple build3 = Apple.builder().name("a1").level("甲3").age(11).build();
        Apple build4 = Apple.builder().name("a2").level("甲4").age(11).build();
        Apple build5 = Apple.builder().name("a2").level("乙5").age(11).build();
        Apple build6 = Apple.builder().name("b2").level("乙6").age(11).build();
        Apple build7 = Apple.builder().name("b3").level("乙8").age(14).build();
        Apple build8 = Apple.builder().name("b3").level("乙8").age(15).build();
        Apple build9 = Apple.builder().name("b3").level("乙9").age(14).build();

        ArrayList<Apple> apples = Lists.newArrayList(build1, build2,
                build3, build4, build5, build6, build7, build8, build9);

        Map<String, List<Apple>> collect = apples.stream().collect(Collectors.groupingBy(item -> item.getName() + "-"
                + item.getLevel()));
        collect.entrySet().forEach(System.out::println);

        System.out.println();

        Map<String, Map<String, List<Apple>>> collect1 = apples.stream().collect(Collectors.groupingBy(Apple::getName
                , Collectors.groupingBy(Apple::getLevel)));
        collect.entrySet().forEach(System.out::println);

        // Apple build1 = Apple.builder().name("a1").build();
        // Apple build2 = Apple.builder().name("a1").build();
        // Apple build3 = Apple.builder().name("a1").build();
        // Apple build4 = Apple.builder().name("a2").build();
        // Apple build5 = Apple.builder().name("a2").build();
        // Apple build6 = Apple.builder().name("b3").build();
        // Apple build7 = Apple.builder().name("b3").build();
        // Apple build8 = Apple.builder().name("b3").build();
        // Apple build9 = Apple.builder().name("b3").build();
        // ArrayList<Apple> apples = Lists.newArrayList(build1, build2,
        //         build3, build4, build5, build6, build7, build8, build9);
        // Map<String, Map<Integer, List<Apple>>> collect1 = apples.stream().collect(Collectors.groupingBy(Apple::getName
        //         , Collectors.groupingBy(d -> -1)));
        // System.out.println(collect1);
    }



}
@Data
// @NoArgsConstructor
@Builder
class Apple {
    private String name;
    private String level;
    private Integer age;
}
