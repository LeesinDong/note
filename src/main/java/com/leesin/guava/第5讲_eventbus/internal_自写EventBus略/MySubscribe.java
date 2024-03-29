package com.leesin.guava.第5讲_eventbus.internal_自写EventBus略;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/21
 * 532500648
 ***************************************/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySubscribe
{
    String topic() default "default-topic";
}
