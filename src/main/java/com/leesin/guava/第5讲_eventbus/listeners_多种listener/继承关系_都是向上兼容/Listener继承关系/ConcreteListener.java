package com.leesin.guava.第5讲_eventbus.listeners_多种listener.继承关系_都是向上兼容.Listener继承关系;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public class ConcreteListener extends BaseListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ConcreteListener.class);

    @Subscribe
    public void conTask(String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("The event [{}] will be handle by {}.{}", event, this.getClass().getSimpleName(), "conTask");
        }
    }
}
