package com.leesin.guava.第6讲_concurrent.RateLimiter.令牌桶;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/12
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class TokenBucket实现令牌桶算法
{
    // cr 本质：在入桶处理的时候加了一个rateLimiter
    // 关于总的100的比较不用介意，不是真实的令牌桶。

    private AtomicInteger phoneNumbers = new AtomicInteger(0);

    private final static int LIMIT = 100;

    private RateLimiter rateLimiter = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket实现令牌桶算法()
    {
        this(LIMIT);
    }

    public TokenBucket实现令牌桶算法(int limit)
    {
        this.saleLimit = limit;
    }

    public int buy()
    {
        Stopwatch started = Stopwatch.createStarted();
        /**
         * 这里的rateLimiter担任的是生产令牌的角色，一秒钟只生产10个。cr 限制处理阶段，匀速入桶 少了一个队列，可以把处理后续理解为一个队列
         *
         * 在指定时间内是否获得令牌，获得了则获得，没获得则返回false
         */
        // cr 本质就这一句
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success)
        {
            // phoneNumbers.get() >= saleLimit 这个应该在锁里面，tryAcquire会加锁，double check也会加锁
            if (phoneNumbers.get() >= saleLimit)
            {
                throw new IllegalStateException("Not any phone can be sale, please wait to next time.");
            }
            int phoneNo = phoneNumbers.getAndIncrement();
            // cr 还有这句
            handleOrder();
            System.out.println(currentThread() + " user get the Mi phone: " + phoneNo + ",ELT:" + started.stop());
            return phoneNo;
        } else
        {
            started.stop();
            throw new RuntimeException("Sorry, occur exception when buy phone");
        }
    }

    private void handleOrder()
    {
        try
        {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
