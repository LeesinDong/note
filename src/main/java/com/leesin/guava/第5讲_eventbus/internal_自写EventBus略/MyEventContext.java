package com.leesin.guava.第5讲_eventbus.internal_自写EventBus略;

import java.lang.reflect.Method;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
public interface MyEventContext
{

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
