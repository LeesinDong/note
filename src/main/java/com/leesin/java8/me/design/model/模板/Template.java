package com.leesin.java8.me.design.model.模板;

import com.google.common.base.Preconditions;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Template {

    // cr 自定义consumer等functional的作用就是提取框架（公共部分），细节再调用处实现


    @Resource
    MyConsumer myConsumer;

    @Test
    public void Test() {
        // cr 方式一 接口子类 == 匿名内部类
        // new 接口子类
        template("leesin", new MyConsumer());
        // spring注入接口子类，单例【优点：1 直接new每次调用template方法都会new一个，资源浪费，会经常触发gc
        // 2 如果MyConsumer中有类级别的变量表示状态，则状态无效，每次new出来的都是初始状态】
        template("leesin", myConsumer);
        // 匿名内部类
        template("leesin", new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // cr 方式二 lambda
        template("leesin", (s) -> consume2(s));

        // cr 方式三 方法引用
        template("leesin", this::consume3);
    }

    /**
     * 模板方法，把公共的提出来，try、加解锁等
     *
     * @param name
     * @param consumer
     */
    public void template(String name, Consumer<String> consumer) {
        Preconditions.checkNotNull(name, "name can not be null");
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            // cr 具体实现
            consumer.accept(name);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }


    /**
     * 具体实现1
     */
    @Component
    class MyConsumer implements Consumer {
        @Override
        public void accept(Object o) {
            System.out.println(o);
        }
    }


    /**
     * 具体实现2
     *
     * @param string
     */
    public void consume2(String string) {
        System.out.println("consume2" + string);
    }

    /**
     * 具体实现3
     *
     * @param string
     */
    public void consume3(String string) {
        System.out.println("consume3" + string);
    }

}
