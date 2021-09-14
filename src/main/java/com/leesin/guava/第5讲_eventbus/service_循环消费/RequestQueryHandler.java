package com.leesin.guava.第5讲_eventbus.service_循环消费;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.leesin.guava.第5讲_eventbus.events略.Request;
import com.leesin.guava.第5讲_eventbus.events略.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/19
 * 532500648
 ***************************************/
public class RequestQueryHandler
{

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void doQuery(Request request)
    {
        LOGGER.info("start query the orderNo [{}]", request.toString());
        Response response = new Response();
        // this.eventBus.post(response);

        EventBus eventBus1 = new EventBus();
        QueryService queryService = new QueryService();
        eventBus1.register(queryService);
        eventBus1.post(response);
    }
}
