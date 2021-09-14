package com.leesin.guava.第7讲_cache.Test;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import com.leesin.guava.第7讲_cache.Employee;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/9
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class CacheLoaderTest3_refresh_pre_notify
{

    @Test
    public void testLoadNullValue()
    {
        CacheLoader<String, Employee> cacheLoader = CacheLoader
                .from(k -> k.equals("null") ? null : new Employee(k, k, k));
        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);

        Employee alex = loadingCache.getUnchecked("Alex");

        assertThat(alex.getName(), equalTo("Alex"));
        try
        {
            /**
             * nullValue判断
             */
            assertThat(loadingCache.getUnchecked("null"), nullValue());
            fail("should not process to here.");
        } catch (Exception e)
        {
//            (expected = CacheLoader.InvalidCacheLoadException.class)
            assertThat(e instanceof CacheLoader.InvalidCacheLoadException, equalTo(true));
        }
    }

    @Test
    public void testLoadNullValueUseOptional()
    {
        CacheLoader<String, Optional<Employee>> loader = new CacheLoader<String, Optional<Employee>>()
        {
            @Override
            public Optional<Employee> load(String key) throws Exception
            {
                if (key.equals("null"))
                /**
                 * 通过optional包装value来操作null
                 */ {
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable(new Employee(key, key, key));
                }
            }
        };

        LoadingCache<String, Optional<Employee>> cache = CacheBuilder.newBuilder().build(loader);
        assertThat(cache.getUnchecked("Alex").get(), notNullValue());
        assertThat(cache.getUnchecked("null").orNull(), nullValue());

        Employee def = cache.getUnchecked("null").or(new Employee("default", "default", "default"));
        assertThat(def.getName().length(), equalTo(7));
    }


    /**
     * cr 更新缓存（本质是删除缓存）
     *  对于结果实时性高的 => 更新数据删除缓存
     *  对于结果实时性低的 => 缓存添加过期时间 refresh
     *
     *  guava的refresh没有通过线程定时清理缓存（删除），而是通过时间戳，超过直接从cacheLoader重新拿取，不会删除缓存，没超过从缓存拿。
     */
    @Test
    public void testCacheRefresh() throws InterruptedException
    {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader
                .from(k ->
                {
                    counter.incrementAndGet();
                    return System.currentTimeMillis();
                });

        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
//                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);

        Long result1 = cache.getUnchecked("Alex");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("Alex");
        assertThat(counter.get(), equalTo(1));
//        assertThat(result1.longValue() != result2.longValue(), equalTo(true));
    }

    /**
     * cr 数据预加载，主动put
     * 缺点：定义的可能和from里面的规则不一致
     */
    @Test
    public void testCachePreLoad()
    {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

        Map<String, String> preData = new HashMap<String, String>()
        {
            {
                // cr
                put("alex", "ALEX");
                put("hello", "hello");
            }
        };

        cache.putAll(preData);
        assertThat(cache.size(), equalTo(2L));
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));
        assertThat(cache.getUnchecked("hello"), equalTo("hello"));
    }

    /**
     * cr 删除通知，记录日志
     */
    @Test
    public void testCacheRemovedNotification()
    {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification ->
        {
            if (notification.wasEvicted())
            {
                /**
                 * 删除通知
                 */
                RemovalCause cause = notification.getCause();
                assertThat(cause, is(RemovalCause.SIZE));
                assertThat(notification.getKey(), equalTo("Alex"));
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //
                .maximumSize(3)
                // cr
                .removalListener(listener)
                //
                .build(loader);
        cache.getUnchecked("Alex");
        cache.getUnchecked("Eachur");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Jenny");
    }
}
