package com.leesin.guava.第5讲_eventbus.test;


import com.leesin.guava.第5讲_eventbus.internal_自写EventBus略.MyAsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
public class MyAsyncBusExample
{

    public static void main(String[] args)
    {
        MyAsyncEventBus eventBus = new MyAsyncEventBus((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
        eventBus.register(new MySimpleListener());
        eventBus.register(new MySimpleListener2());
        eventBus.post(123131, "alex-topic");
        eventBus.post("hello");

    }
}
