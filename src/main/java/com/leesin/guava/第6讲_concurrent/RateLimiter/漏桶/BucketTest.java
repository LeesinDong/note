package com.leesin.guava.第6讲_concurrent.RateLimiter.漏桶;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/8
 ***************************************/
public class BucketTest
{

    public static void main(String[] args)
    {
        final Bucket_guava实现漏桶算法 bucket = new Bucket_guava实现漏桶算法();
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        IntStream.range(0, 5).forEach(i ->
        {
            new Thread(() ->
            {
                for (; ; )
                {
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.submit(data);
                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(200L);
                    } catch (Exception e)
                    {
                        if (e instanceof IllegalStateException)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                }       //25

                //10

                // cr 放 1s放5个，5个线程1s放25个，
                //    处理 1s 处理10get
                //  故最终的不论任何时间，放 : 处理 = 5 : 2
                //5:2
            }).start();
        });


        IntStream.range(0, 5)
                .forEach(i -> new Thread(() ->
                        {
                            for (; ; )
                            {
                                bucket.takeThenConsume(x -> System.out.println(currentThread() + " W " + x));
                            }
                        }).start()
                );
    }
}
