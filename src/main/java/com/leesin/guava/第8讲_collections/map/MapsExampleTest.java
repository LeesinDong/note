package com.leesin.guava.第8讲_collections.map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/14
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class MapsExampleTest
{

    /**
     * 只看创建
     * 后面的用stream即可
     */
    @Test
    public void testCreate()
    {
        ArrayList<String> valueList = Lists.newArrayList("1", "2", "3");
        // cr  uniqueIndex 修饰key
        ImmutableMap<String, String> map = Maps.uniqueIndex(valueList, v -> v + "_key");
        System.out.println(map);
        // cr asMap 修饰 value
        HashSet<String> set = Sets.newHashSet("1", "2", "3");
        Map<String, String> map2 = Maps.asMap(set, k -> k + "_value");
        System.out.println(map2);
    }

    /**
     * 就是map
     */
    @Test
    public void testTransform()
    {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        Map<String, String> newMap = Maps.transformValues(map, v -> v + "_transform");
        System.out.println(newMap);
        assertThat(newMap.containsValue("1_value_transform"), is(true));
    }

    /**
     * 就是filter
     */
    @Test
    public void testFilter()
    {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        Map<String, String> newMap = Maps.filterKeys(map, k -> Lists.newArrayList("1", "2").contains(k));
        assertThat(newMap.containsKey("3"), is(false));
    }
}