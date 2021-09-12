package com.leesin.guava.第5讲_eventbus.listeners_多种listener.简单Listener;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public class MultipleEventListeners
{

    private final static Logger LOGGER = LoggerFactory.getLogger(MultipleEventListeners.class);

    // cr String消息 会同时执行task1 task2
    //    Integer消息，会执行intTask（参数只能是Integer，不能是int）

    @Subscribe
    public void task1(String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("the event [{}] received and will take a action by ==task1== ", event);
        }
    }

    @Subscribe
    public void task2(String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("the event [{}] received and will take a action by ==task2== ", event);
        }
    }

    @Subscribe
    public void intTask(Integer event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("the event [{}] received and will take a action by ==intTask== ", event);
        }
    }
}
