package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.EventBus;
import com.leesin.guava.第5讲_eventbus.listeners_多种listener.DeadEvent.DeadEventListener;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class DeadEventBusExample
{

    public static void main(String[] args)
    {

        final DeadEventListener deadEventListener = new DeadEventListener();
        final EventBus eventBus = new EventBus("DeadEventBus")
        {
            @Override
            public String toString()
            {
                return "DEAD-EVENT-BUS";
            }
        };
        eventBus.register(deadEventListener);
        eventBus.post("Hello");


        /**
         * cr unregister，将listener从event bus 中移除出去，后面发送消息，该listener将接收不到了
         */
        eventBus.unregister(deadEventListener);
        eventBus.post("Hello");
    }
}
