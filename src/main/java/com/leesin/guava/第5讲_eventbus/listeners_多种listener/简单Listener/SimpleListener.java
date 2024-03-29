package com.leesin.guava.第5讲_eventbus.listeners_多种listener.简单Listener;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public class SimpleListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @Subscribe
    /**
     * subscribe方法 只能有一个参数
     */
    public void doAction(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action", event);
        }
    }

    @Subscribe
    public void doAction1(final String event)
    {

        try
        {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action1", event);
        }
    }

    @Subscribe
    public void doAction2(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }

    @Subscribe
    public void doAction3(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }

    @Subscribe
    public void doAction4(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }
}
