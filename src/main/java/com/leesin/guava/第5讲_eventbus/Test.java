package com.leesin.guava.第5讲_eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        // EventBus eventBus = new EventBus();
        // EventBus eventBus = new AsyncEventBus(Runnable::run);
        // EventBus eventBus = new AsyncEventBus(new Sequence());
        EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));
        eventBus.register(new Sub());
        eventBus.post("1245");
    }

    static class Sequence implements Executor {
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }


    static class Sub{
        @Subscribe
        public void sub(String message) throws InterruptedException {
            System.out.println(message);
            Thread.sleep(3000);
            throw new RuntimeException();
        }

        @Subscribe
        public void sub1(String message) {
            System.out.println(message);
        }
    }
}
