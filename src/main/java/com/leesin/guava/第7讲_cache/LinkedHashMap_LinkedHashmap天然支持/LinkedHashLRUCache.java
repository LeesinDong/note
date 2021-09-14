package com.leesin.guava.第7讲_cache.LinkedHashMap_LinkedHashmap天然支持;

import com.google.common.base.Preconditions;
import com.leesin.guava.第7讲_cache.引用.软引用cache略.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/13
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/

/**
 * This class is not the thread-safe class.
 *
 * @param <K>
 * @param <V>
 */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V>
{
    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V>
    {
        final private int limit;

        public InternalLRUCache(int limit)
        {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        /**
         * 重写什么时候删除最老的的函数，在put的时候会用到
         */
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
        {
            return size() > limit;
        }
    }

    private final int limit;

    private final InternalLRUCache<K, V> internalLRUCache;


    public LinkedHashLRUCache(int limit)
    {
        Preconditions.checkArgument(limit > 0, "The limit big than zero.");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }

    @Override
    /**
     * put的时候如果队列满了，删掉最老的，并插入当前
     */
    public void put(K key, V value)
    {
        this.internalLRUCache.put(key, value);
    }

    @Override
    /**
     * get的时候从队列中删掉，并插入到最后面
     */
    public V get(K key)
    {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key)
    {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size()
    {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear()
    {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit()
    {
        return this.limit;
    }

    @Override
    public String toString()
    {
        return internalLRUCache.toString();
    }
}