package com.leesin.java8.me.design;

import java.util.function.Supplier;

public class Retry {
    public static void Retry(Supplier<Boolean> supplier, int retryTime) {
        boolean flag = false;
        int time = 0;
        while (!flag && time < retryTime) {
            flag = supplier.get();
            time++;
        }
    }

}
