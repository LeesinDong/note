package com.leesin.guava.第8讲_collections.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/14
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class BiMapExampleTest
{

    // cr value不能一致

    @Test
    public void testCreateAndPut()
    {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("1", "3");
        assertThat(biMap.containsKey("1"), is(true));
        assertThat(biMap.size(), equalTo(1));

        try
        {
            /**
             * cr 和HashMap一样，key相等覆盖
             *  多了：value相等，会报错，保证value唯一性
             */
            biMap.put("2", "3");
            fail();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * cr key value 倒过来
     */
    @Test
    public void testBiMapInverse()
    {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("2", "3");
        biMap.put("3", "4");

        assertThat(biMap.containsKey("1"), is(true));
        assertThat(biMap.containsKey("2"), is(true));
        assertThat(biMap.containsKey("3"), is(true));
        assertThat(biMap.size(), equalTo(3));

        // cr
        BiMap<String, String> inverseKey = biMap.inverse();
        assertThat(inverseKey.containsKey("2"), is(true));
        assertThat(inverseKey.containsKey("3"), is(true));
        assertThat(inverseKey.containsKey("4"), is(true));
        assertThat(inverseKey.size(), equalTo(3));
    }

    /**
     * cr 强制put value相等的，原来的会被覆盖
     */
    @Test
    public void testCreateAndForcePut()
    {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        assertThat(biMap.containsKey("1"), is(true));
        // cr
        biMap.forcePut("2", "2");
        assertThat(biMap.containsKey("1"), is(false));
        assertThat(biMap.containsKey("2"), is(true));
    }
}