package com.leesin.guava.第8讲_collections.list;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/13
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class ListsExampleTest
{

    @Test
    public void testCreate() {
        // Arrays.asList 等同于 Lists.newArrayList 等同于 Lists.asList
        // 但是Lists.asList返回的是不可变的列表
        List<String> List1 = Arrays.asList("a", "b", "c");
        List<String> List2 = Lists.<String>asList("a", "b", new String[]{"a", "b"});
        ArrayList<String> List3 = Lists.newArrayList("a", "b", "c");
    }

    @Test
    public void testCartesianProduct()
    {
        /**
         * cr 笛卡尔积
         */
        List<List<String>> result = Lists.cartesianProduct(
                Lists.newArrayList("1", "2"),
                Lists.newArrayList("A", "B")
        );
        System.out.println(result);
    }

    @Test
    public void testTransform()
    {
        /**
         * cr 相当于 stream.map
         */
        ArrayList<String> sourceList = Lists.newArrayList("Scala", "Guava", "Lists");
        Lists.transform(sourceList, e -> e.toUpperCase()).forEach(System.out::println);
    }

    @Test
    public void testNewArrayListWithCapacity()
    {
        /**
         * cr 定义初始化大小
         */
        ArrayList<String> result = Lists.newArrayListWithCapacity(10);
        result.add("x");
        result.add("y");
        result.add("z");
        System.out.println(result);


    }

    //Apache NIFI
    //Hotworks HDF
    @Test
    public void testNewArrayListWithExpectedSize(){
        /**
         * cr 特殊的定义初始化大小，比如 输入10，则为11
         */
        Lists.newArrayListWithExpectedSize(5);
    }

    @Test
    public void testReverse(){
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list),equalTo("1,2,3"));
        /**
         * cr 反转
         */
        List<String> result = Lists.reverse(list);
        assertThat(Joiner.on(",").join(result),equalTo("3,2,1"));
    }


    @Test
    public void testPartition(){
        ArrayList<String> list = Lists.newArrayList("1", "2", "3","4");
        /**
         * cr 分区
         */
        List<List<String>> result = Lists.partition(list, 30);
        System.out.println(result.get(0));
    }
}