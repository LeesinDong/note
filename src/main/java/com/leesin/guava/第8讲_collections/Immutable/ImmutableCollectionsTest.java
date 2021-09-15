package com.leesin.guava.第8讲_collections.Immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/15
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class ImmutableCollectionsTest
{
    // cr 不可变， 对多线程有好处，只读

    /**
     * 创建ImmutableList1
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testOf()
    {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertThat(list, notNullValue());
        list.add(4);
        fail();
    }

    /**
     * copyOf 2
     */
    @Test
    public void testCopy()
    {
        Integer[] array = {1, 2, 3, 4, 5};
        System.out.println(ImmutableList.copyOf(array));
    }

    /**
     * 创建ImmutableList builder 方式 3
     */
    @Test
    public void testBuilder()
    {
        ImmutableList<Integer> list = ImmutableList.<Integer>builder()
                .add(1)
                .add(2, 3, 4).addAll(Arrays.asList(5, 6))
                .build();
        System.out.println(list);
    }

    /**
     * 创建4  immutableMap
     */
    @Test
    public void testImmutableMap()
    {
        // 方式一
        // 报错不要管
        ImmutableMap<Integer, Integer> of = ImmutableMap.of(1, 2).of(3, 4, 5 ,6);

        // 方式二
        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder().put("Oracle", "12c")
                .put("Mysql", "7.0").build();
        System.out.println(map);
        try
        {
            map.put("Scala", "2.3.0");
            fail();
        } catch (Exception e)
        {
            assertThat(e instanceof UnsupportedOperationException, is(true));
        }
    }
}