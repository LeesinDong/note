package com.leesin.guava.第6讲_concurrent.RateLimiter.漏桶;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/***************************************
 ***************************************/
public class Bucket_guava实现漏桶算法
{

    // cr 本质：放入（monitor） + 处理（rateLimiter）

    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    // 桶的上限
    private final static int BUCKET_LIMIT = 1000;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor offerMonitor = new Monitor();
    private final Monitor pollMonitor = new Monitor();


    /**
     * 放入元素，溢出的进行兜底降级 - Monitor
     */
    public void submit(Integer data)
    {
        // 通过monitor实现阻塞溢出的进行降级处理
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> container.size() < BUCKET_LIMIT)))
        {
            try
            {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ",current size:" + container.size());
            } finally
            {
                offerMonitor.leave();
            }
        } else
        {
            // 这里可以降级处理，返回页面相应的提示
            throw new IllegalStateException("The bucket is full.");
        }
    }


    /**
     * 处理元素：匀速处理 - rateLimiter
     */
    public void takeThenConsume(Consumer<Integer> consumer)
    {
        if (pollMonitor.enterIf(pollMonitor.newGuard(() -> !container.isEmpty())))
        {
            try
            {
                /**
                 * rateLimit加在了漏桶的出桶，入桶不管，cr 加在了处理阶段，匀速出桶，出队列，多了一个队列
                 */
                // limiter.acquire 返回睡眠的时间
                System.out.println(currentThread() + " waiting " + limiter.acquire());
                // 睡眠相应的时间后，拿到东西进行消费
                consumer.accept(container.poll());
            } finally
            {
                pollMonitor.leave();
            }
        }
    }
}
