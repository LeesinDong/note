package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.EventBus;
import com.leesin.guava.第5讲_eventbus.listeners_多种listener.简单Listener.SimpleListener;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public class SimpleEventBusExample
{
    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        /**
         * 注册的方法根据消息类型进行 匹配 listener
         */
        eventBus.post("Simple Event");
    }
}
