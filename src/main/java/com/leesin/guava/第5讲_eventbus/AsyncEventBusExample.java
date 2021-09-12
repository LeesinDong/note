package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.leesin.guava.第5讲_eventbus.listeners_多种listener.简单Listener.SimpleListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
public class AsyncEventBusExample
{
    public static void main(String[] args)
    {
        /**
         * 异步体现在异步的 反射执行多个subscribe方法
         * 到底是否能够异步取决于，里面的Executor，如果是线程池类型的则可以（相互之间不阻塞，异步执行）（如eventBus）
         * 如果是简单的Runnable的run方法则是同步执行（只能一个一个执行，前面会阻塞后面的）（如eventBus1）
         */
        AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));

        AsyncEventBus eventBus1 = new AsyncEventBus(new SeqExecutor());
        eventBus.register(new SimpleListener());
        eventBus.post("hello");

    }

    static class SeqExecutor implements Executor
    {

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }
}
