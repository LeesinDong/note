package com.leesin.guava.第7讲_cache.LinkedList_和leetcode思想一致;

import com.leesin.guava.第7讲_cache.引用.软引用cache略.LRUCache;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/13
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class LinkedListLRUCacheExample
{

    public static void main(String[] args)
    {
        LRUCache<String, String> cache = new LinkedListLRUCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        System.out.println(cache);
        cache.put("4", "4");

        System.out.println(cache);

        System.out.println(cache.get("2"));
        System.out.println(cache);

    }
}
