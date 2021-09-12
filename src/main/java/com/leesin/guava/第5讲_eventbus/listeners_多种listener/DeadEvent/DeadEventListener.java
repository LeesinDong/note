package com.leesin.guava.第5讲_eventbus.listeners_多种listener.DeadEvent;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class DeadEventListener
{
    /**
     * DeadEvent类型也可以接收String
     * 这里的作用就是能够获取 source（event bus） 和 event（消息）
     */
    @Subscribe
    public void handle(DeadEvent event)
    {
        System.out.println(event.getSource());
        System.out.println(event.getEvent());
    }
}
