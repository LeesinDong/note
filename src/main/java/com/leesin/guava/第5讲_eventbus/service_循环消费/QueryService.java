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
public class QueryService
{

    private final static Logger LOGGER = LoggerFactory.getLogger(QueryService.class);

    private  EventBus eventBus;

    public QueryService() {
    }

    public QueryService(EventBus eventBus)
    {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void query(String orderNo)
    {
        LOGGER.info("Received the orderNo [{}]", orderNo);
        this.eventBus.post(new Request(orderNo));
    }

    @Subscribe
    public void handleResponse(Response response)
    {
        LOGGER.info("{}", response);
    }
}
