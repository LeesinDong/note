package com.leesin.guava.第5讲_eventbus.listeners_多种listener.继承关系_都是向上兼容.Listener继承关系;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public abstract class AbstractListener
{

    // cr 注册子类listener，所有的父类和自己都会监听到消息
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractListener.class);

    @Subscribe
    public void commonTask(String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("The event [{}] will be handle by {}.{}", event, this.getClass().getSimpleName(), "commonTask");
        }
    }
}
