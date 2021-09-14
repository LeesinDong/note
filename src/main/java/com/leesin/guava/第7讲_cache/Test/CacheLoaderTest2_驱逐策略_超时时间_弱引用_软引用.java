package com.leesin.guava.第7讲_cache.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.leesin.guava.第7讲_cache.Employee;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/18
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class CacheLoaderTest2_驱逐策略_超时时间_弱引用_软引用
{

    /**
     * cr 驱逐策略3 超时时间
     *
     * TTL->time to live
     * Access time => Write/Update/Read
     *
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByAccessTime() throws InterruptedException
    {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());
        /**
         * cr getUnchecked 没有就从db里面拿，get也是，get会强制抛异常
         */
        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        /**
         * cr 有就有，没有就没有，不会再添加到缓存
         */
        assertThat(cache.getIfPresent("Alex"), nullValue());

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());

        /**
         * cr 超时自动逐出
         *  过了三秒但是还是没有逐出 Guava的，因为expireAfterAccess是指write、update、read之后更新的时间计算
         */
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
    }

    /**
     * cr 驱逐策略3 超时时间
     * Write time => write/update
     */
    @Test
    public void testEvictionByWriteTime() throws InterruptedException
    {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                // cr expireAfterWrite
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.MILLISECONDS.sleep(990);
        assertThat(cache.getIfPresent("Guava"), notNullValue());

        /**
         * 这里是notNull会报错，expireAfterWrite，read不会更新expire时间
         */
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), nullValue());
    }

    /**
     * 驱逐策略4 弱引用
     * gc的时候驱逐
     */
    @Test
    public void testWeakKey() throws InterruptedException
    {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                // cr
                .weakValues()
                .weakKeys()
                .build(this.createCacheLoader());
        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.getUnchecked("Guava"), notNullValue());

        //active method
        //Thread Active design pattern
        System.gc();
        // system.gc 是系统的守护线程进行的优先级很低，所以等待执行完成
        TimeUnit.MILLISECONDS.sleep(100);
        assertThat(cache.getIfPresent("Alex"), nullValue());
    }

    /**
     * cr 驱逐策略5 软引用
     * 快要oom的时候自动gc
     */
    @Test
    public void testSoftKey() throws InterruptedException
    {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                // cr
                .softValues()
                .build(this.createCacheLoader());
        int i = 0;
        for (; ; )
        {
            cache.put("Alex" + i, new Employee("Alex" + 1, "Alex" + 1, "Alex" + 1));
            System.out.println("The Employee [" + (i++) + "] is store into cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    // cr 创建默认缓存的方式二
    private CacheLoader<String, Employee> createCacheLoader()
    {
        /**
         * cacheLoader可以new
         * 可以from(Supplier)
         * 可以from(function)
         */
        return CacheLoader.from(key -> new Employee(key, key, key));
    }
}
