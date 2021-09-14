package com.leesin.guava.第8讲_collections.Ordering;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/15
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class OrderingExampleTest
{

    /**
     * cr jdk自然顺序排序
     */
    @Test
    public void testJDKOrder()
    {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        System.out.println(list);
        //
        Collections.sort(list);
        System.out.println(list);
    }

    /**
     * cr 问题：有null的时候，空指针
     */
    @Test(expected = NullPointerException.class)
    public void testJDKOrderIssue()
    {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    /**
     * cr guava解决null问题
     * Ordering.natural().nullsFirst() ，null放在第一个，
     * 并自然顺序排序,就是实实在在的null，不是字符串也不会抛异常
     */
    @Test
    public void testOrderNaturalByNullFirst()
    {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
    }

    /**
     * cr null放最后一个
     */
    @Test
    public void testOrderNaturalByNullLast()
    {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    /**
     * cr Ordering.natural().isOrdered(list), 是否是自然顺序
     */
    @Test
    public void testOrderNatural()
    {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list);
        assertThat(Ordering.natural().isOrdered(list), is(true));
    }


    /**
     * cr Ordering.natural().reverse() 自然顺序逆序
     */
    @Test
    public void testOrderReverse()
    {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list, Ordering.natural().reverse());
        System.out.println(list);
    }
}