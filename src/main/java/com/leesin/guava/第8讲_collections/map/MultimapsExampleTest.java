package com.leesin.guava.第8讲_collections.map;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/14
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class MultimapsExampleTest
{

    @Test
    public void testBasic()
    {
        /**
         * 相当于实现 Map<String, List<>> 或 Map<String, Set<>
         */
        LinkedListMultimap<String, String> multipleMap = LinkedListMultimap.create();
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("1", "1");
        hashMap.put("1", "2");
        assertThat(hashMap.size(), equalTo(1));


        multipleMap.put("1", "1");
        multipleMap.put("1", "2");
        assertThat(multipleMap.size(), equalTo(2));
        // 返回list [1, 2]
        System.out.println(multipleMap.get("1"));
    }
}