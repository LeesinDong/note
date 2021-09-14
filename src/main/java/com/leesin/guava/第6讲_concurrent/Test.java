package com.leesin.guava.第6讲_concurrent;


import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.LinkedList;

public class Test {
    static Monitor monitor = new Monitor();
    LinkedList<Integer> queue = new LinkedList<>();
    Monitor.Guard canOffer = monitor.newGuard(() -> queue.size() < 10);
    Monitor.Guard canTake = monitor.newGuard(() -> queue.size() > 0);

    RateLimiter limi = RateLimiter.create(10);

    public static void main(String[] args) {
    }

    public void offer() {
        try {
            monitor.enterWhen(canOffer);
            queue.add(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.leave();
        }
    }

    public void take() {
        try {
            System.out.println("limit" + limi.acquire());
            monitor.enterWhen(canTake);
            queue.removeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.leave();
        }
    }

    public  void get() {
        boolean b = limi.tryAcquire();
        if (b) {
            handler();
        }
    }

    private void handler() {

    }
}
