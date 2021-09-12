package com.leesin.guava.第5讲_eventbus.internal_自写EventBus略;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
public interface MyEventExceptionHandler
{
    void handle(Throwable cause, MyEventContext context);
}
