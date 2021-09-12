package com.leesin.guava.第6讲_concurrent.RateLimiter.漏桶;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/8
 ***************************************/
public class RateLimiterExample_漏桶
{

    /**
     * 令牌桶：匀速，控制速率
     * create(double permitsPerSecond)
     * permitsPerSecond 一秒钟允许n次操作，0.5 即为 2s 1次
     */
    private final static RateLimiter limiter = RateLimiter.create(0.5d);

    /**
     * 同一时刻只能有三个线程工作
     */
    private final static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args)
    {
        // 固定 两秒输出一次
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i ->
                service.submit(RateLimiterExample_漏桶::testSemaphore)
                // service.submit(RateLimiterExample_漏桶::testLimiter)
        );
    }

    private static void testLimiter()
    {
        System.out.println(currentThread() + " waiting " + limiter.acquire());
    }

    private static void testSemaphore()
    {

        try
        {
            semaphore.acquire();
            System.out.println(currentThread() + " is coming and do work.");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } finally
        {
            semaphore.release();
            System.out.println(currentThread() + " release the semaphore.");
        }
    }
}
