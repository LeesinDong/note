package com.leesin.guava.第5讲_eventbus.test;


import com.leesin.guava.第5讲_eventbus.internal_自写EventBus略.MySubscribe;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
public class MySimpleListener
{

    @MySubscribe
    public void test1(String x)
    {
        System.out.println("MySimpleListener===test1==" + x);
    }

    @MySubscribe(topic = "alex-topic")
    public void test2(Integer x)
    {
        System.out.println("MySimpleListener===test2==" + x);
    }
}
