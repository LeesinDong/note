package com.leesin.guava.第7讲_cache.Test;

import com.google.common.cache.*;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/13
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class CacheLoaderTest4_states_spec
{

    @Test
    public void testCacheStat()
    {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        /**
         * recordStats记录命中率
         */
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5).recordStats().build(loader);
        assertCache(cache);
    }

    @Test
    /**
     * 通过 环境变量 或 配置文件的方式 而不是builder的方式 苟江LoaderCache
     */
    public void testCacheSpec()
    {
        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec builderSpec = CacheBuilderSpec.parse(spec);
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(builderSpec).build(loader);

        assertCache(cache);
    }

    private void assertCache(LoadingCache<String, String> cache)
    {
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));//ALEX
        // cr CacheStats
        CacheStats stats = cache.stats();
        System.out.println(stats.hashCode());
        assertThat(stats.hitCount(), equalTo(0L));
        assertThat(stats.missCount(), equalTo(1L));

        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));

        /**
         * cr stats是不变的，所以每次需要重新获取，便于并发操作
         */
        stats = cache.stats();
        System.out.println(stats.hashCode());
        /**
         * cr 命中数
         */
        assertThat(stats.hitCount(), equalTo(1L));
        /**
         *cr miss数
         */
        assertThat(stats.missCount(), equalTo(1L));

        /**
         * cr miss率 命中率
         */
        System.out.println(stats.missRate());
        System.out.println(stats.hitRate());
    }
}
