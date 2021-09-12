package com.leesin.guava.第5讲_eventbus.listeners_多种listener.继承关系_都是向上兼容.Event继承关系;

import com.google.common.eventbus.Subscribe;
import com.leesin.guava.第5讲_eventbus.events略.Apple;
import com.leesin.guava.第5讲_eventbus.events略.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/18
 * 532500648
 ***************************************/
public class FruitEaterListener
{

    private final static Logger LOGGER = LoggerFactory.getLogger(FruitEaterListener.class);

    // cr Applie会走两个 listener
    //  Fruit只会走这个一个listner
    @Subscribe
    public void eat(Fruit event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Fruit eat [{}].", event);
        }
    }

    @Subscribe
    public void eat(Apple event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Apple eat [{}].", event);
        }
    }
}
