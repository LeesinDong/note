package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.EventBus;
import com.leesin.guava.第5讲_eventbus.listeners_多种listener.Exception.ExceptionListener;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class ExceptionEventBusExample
{
    public static void main(String[] args)
    {
        /**
         * 匿名内部类代替下面的ExceptionHandler
         * 因为最终和这个Exception并没有抛出去，而是通过try catch住了，只是打印了错误的堆栈信息，catch里面就是SubscriberExceptionHandler，所以这里重写这个Handler
         * 可以获取相关的信息
         * 且不会影响后面的subscribe，所以后面的可以继续消费，不会阻塞event bus
         */
        final EventBus eventBus = new EventBus((exception, context) ->
        {
            // 用于记录错误信息
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        });
        eventBus.register(new ExceptionListener());

        eventBus.post("exception post");
    }

/*
    static class ExceptionHandler implements SubscriberExceptionHandler
    {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context)
        {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }
    }*/

}
