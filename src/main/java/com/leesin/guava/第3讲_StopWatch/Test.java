package com.leesin.guava.第3讲_StopWatch;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        Stopwatch started = Stopwatch.createStarted();

        started.stop();

        started.stop().elapsed(TimeUnit.HOURS);

        started.reset();

        Stopwatch.createUnstarted().start();

    }
}
