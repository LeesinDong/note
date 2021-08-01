package com.leesin.heightConcurrent.executor;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/3/26 20:50
 * @version: ${VERSION}
 * @modified By:
 */
public class Monitor extends ThreadPoolExecutor {

    //保存任务开始执行的时间，当任务结束是，用任务结束时间减去开始时间计算任务执行时间
    private ConcurrentHashMap<String, Date> startTimes;

    public Monitor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                   TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.startTimes = new ConcurrentHashMap<>();
    }

    @Override
    public void shutdown() {
        System.out.println("已经执行的任务数：" + this.getCompletedTaskCount() + "," +
                "当前活动线程数：" + this.getActiveCount() + ",当前排队线程数：" + this.getQueue().size());
        System.out.println();
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimes.put(String.valueOf(r.hashCode()), new Date());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
        Date finishDate = new Date();
        long diff = finishDate.getTime() - startDate.getTime();
        // 统计任务耗时、初始线程数、核心线程数、正在执行的任务数量、
        // 已完成任务数量、任务总数、队列里缓存的任务数量、
        // 池中存在的最大线程数、最大允许的线程数、线程空闲时间、线程池是否关闭、线程池
        //         是否终止
        System.out.println("任务耗时 " + diff);
        System.out.println("初始化线程数 " + this.getPoolSize());
        System.out.println("核心线程数 " + this.getCorePoolSize());
        System.out.println("正在执行的任务数量: " + this.getActiveCount());
        System.out.println("已经执行的任务 " + this.getCompletedTaskCount());
        System.out.println("任务总数 " + this.getTaskCount());
        System.out.println("最大允许的线程数 " + this.getMaximumPoolSize());
        System.out.println("线程空闲时间 " + this.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("当前排队线程数" + this.getQueue().size());
        super.afterExecute(r, t);
    }

    public static ExecutorService newCachedThreadPool() {
        return new Monitor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}
