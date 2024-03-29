package com.leesin.java8.wangwenjun.第12讲_collector.slightly;

import com.leesin.java8.wangwenjun.第12讲_collector.ToListCollector_自定义Collector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/30 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CustomerCollectorAction {

    public static void main(String[] args) {
        Collector<String, List<String>, List<String>> collector = new ToListCollector_自定义Collector<>();

        String[] arrs = new String[]{"Alex", "Wang", "Hello", "Lambda", "Collector", "Java 8", "Stream"};

        List<String> result = Arrays.asList("Alex", "Wang", "Hello", "Lambda", "Collector", "Java 8", "Stream")
                .parallelStream()
                .filter(s -> s.length() >= 5)
                .collect(collector);

        System.out.println(result);

    }
}