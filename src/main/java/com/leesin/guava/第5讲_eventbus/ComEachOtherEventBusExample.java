package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.EventBus;
import com.leesin.guava.第5讲_eventbus.service_循环消费.QueryService;
import com.leesin.guava.第5讲_eventbus.service_循环消费.RequestQueryHandler;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class ComEachOtherEventBusExample
{

    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();
        // 注册到一个eventbus了，这只是例子，我认为可以不再一个eventBus
        // service会注册本身到eventBus
        QueryService queryService = new QueryService(eventBus);
        // 注册consumer到eventBus
        eventBus.register(new RequestQueryHandler(eventBus));
        /**
         * cr 调用了query，service将request放入------event bus--------consumer消费response放入-------event
         *  bus-----Service再次消费response
         *  形成循环消费
         *  service（request）《-------》event bus 《-------》consumer（response）
         *  【这里的例子共用了eventBus，也可以不共用但是要订阅和发送的一样】
         */
        queryService.query("werwersdf");
    }
}
