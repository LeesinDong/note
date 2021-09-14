package com.leesin.guava.第8讲_collections.set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 ***************************************/
public class SetsExampleTest
{

    @Test
    public void testCreate()
    {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        assertThat(set.size(), equalTo(3));

        ArrayList<Integer> list = Lists.newArrayList(1, 1, 2, 3);
        assertThat(list.size(), equalTo(4));

        HashSet<Integer> set2 = Sets.newHashSet(list);
        assertThat(set2.size(), equalTo(3));


    }

    /**
     * cr 笛卡尔积
     */
    @Test
    public void testCartesianProduct()
    {
        // cr 这里返回的是Set<List>, 所以每个结果里面不会去重，但是大结果会去重
        Set<List<Integer>> set = Sets.cartesianProduct(Sets.newHashSet(1, 2), Sets.newHashSet(1, 4),
                Sets.newHashSet(5, 6));

        System.out.println(set);


    }


    /**
     * cr 子集（子集 & 真子集）
     */
    @Test
    public void testCombinations()
    {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        // 第二个参数，每个子集的大小
        Set<Set<Integer>> combinations = Sets.combinations(set, 2);
        combinations.forEach(System.out::println);

        Set<Set<Integer>> combinations1 = Sets.combinations(set, 3);
        combinations1.forEach(System.out::println);
    }

    /**
     * cr 差集，在第一个参数里面但是没有在第二个参数里面的
     */
    @Test
    public void testDiff()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.SetView<Integer> diffResult1 = Sets.difference(set1, set2);
        System.out.println(diffResult1);
        Sets.SetView<Integer> diffResult2 = Sets.difference(set2, set1);
        System.out.println(diffResult2);


    }

    /**
     * cr 交集
     */
    @Test
    public void testIntersection()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.intersection(set1, set2).forEach(System.out::println);
    }


    /**
     * cr 并集
     */
    @Test
    public void testUnionSection()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.union(set1, set2).forEach(System.out::println);
    }
}
