package com.leesin.guava.第7讲_cache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class testTest {
    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10)
                .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key + "post";
                    }
                });

        assertThat(cache.get("1"), notNullValue());


        Weigher<String, String> wei = (Weigher<String, String>) (key, value) -> {
            return value.length();
        };

        CacheBuilder.newBuilder().maximumWeight(45)
                .concurrencyLevel(1)
                .weigher(wei);

        CacheStats stats = cache.stats();
        stats.missCount();
        stats.missRate();
        stats.hitCount();
        stats.hitRate();
    }


}
