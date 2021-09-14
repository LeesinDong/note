package com.leesin.guava.第7讲_cache.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import com.leesin.guava.第7讲_cache.Employee;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
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
public class CacheLoaderTest1_驱逐策略_默认_权重
{

    private boolean isTrue = false;

    @Test
    public void testBasic() throws ExecutionException, InterruptedException
    {
        // cr
                                                                        // 最大大小
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(10)
            /**
             * 超 时时间
             */
            .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                .build(createCacheLoader());

        Employee employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();
        /**
         * cr get，根据key获取, 没有会创建
         */
        employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        assertLoadFromCache();

        TimeUnit.MILLISECONDS.sleep(31);
        employee = cache.get("Alex");

        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();
    }

    /**
     * cr 驱逐策略1
     *  就是普通的驱逐最旧的
     */
    @Test
    public void testEvictionBySize()
    {
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumSize(3).build(cacheLoader);

        // cr getUnchecked 和 get的区别，get会强制被调用的地方抛出getUnchecked，getUnchecked不需要
        cache.getUnchecked("Alex");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Jack");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        assertThat(cache.size(), equalTo(3L));
        /**
         * cr 加入susan会自动将alex驱逐出去，最老的
         */
        cache.getUnchecked("Susan");
        assertThat(cache.getIfPresent("Alex"), nullValue());
        assertThat(cache.getIfPresent("Susan"), notNullValue());
    }

    /**
     * 驱逐策略2
     * 在右权重的基础上，驱逐最旧的
     */
    @Test
    public void testEvictionByWeight()
    {
        Weigher<String, Employee> weigher = (key, employee) ->
                employee.getName().length() + employee.getEmpID().length() + employee.getDept().length();

        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                /**
                 * 总的权重，到这这个数值则进行驱逐
                 */
                .maximumWeight(45)
                /**
                 * 并发级别8，指同时写缓存的数量，驱逐策略的时候设置为1，防止，驱逐的速度 < 写入的速度
                 */
                .concurrencyLevel(1)
                /**
                 * 称重的方式，如何计算每个的权重
                 */
                .weigher(weigher)
                .build(createCacheLoader());

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Kevin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Allen");
        assertLoadFromDBThenReset();
        assertThat(cache.size(), equalTo(3L));
        assertThat(cache.getIfPresent("Gavin"), notNullValue());

        // cr 加入新的，会自动把旧的挤出去
        cache.getUnchecked("Jason");

        assertThat(cache.getIfPresent("Kevin"), nullValue());
        assertThat(cache.size(), equalTo(3L));
    }

    private CacheLoader<String, Employee> createCacheLoader()
    {
        // cr 缓存中没有的时候如何获取
        return new CacheLoader<String, Employee>()
        {
            @Override
            public Employee load(String key) throws Exception
            {
                return findEmployeeByName(key);
            }
        };
    }


    private void assertLoadFromDBThenReset()
    {
        assertThat(true, equalTo(isTrue));
        this.isTrue = false;
    }

    private void assertLoadFromCache()
    {
        assertThat(false, equalTo(isTrue));
    }

    private Employee findEmployeeByName(final String name)
    {
        //System.out.println("The employee " + name + " is load from DB.");
        isTrue = true;
        return new Employee(name, name, name);
    }
}