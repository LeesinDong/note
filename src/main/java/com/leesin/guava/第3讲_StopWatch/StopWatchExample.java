package com.leesin.guava.第3讲_StopWatch;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/11
 * @QQ: 532500648
 ***************************************/
public class StopWatchExample {
    private final static Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);

    // cr 写日志最好用英文，对于日志的抓取、正则匹配等都有好处

    public static void main(String[] args) throws InterruptedException {
        process("3463542353");
    }

    /**
     * drools
     * @param orderNo
     * @throws InterruptedException
     */
    private static void process(String orderNo) throws InterruptedException {

        /**
         * 本质就是ticker.read()方法，ticker.read()方法本质就是System.nanoTime();
         * createUnstarted，只create不starrt
         * createStart，先create顺便start
         * stop() 会返回具体的值，而且会自适应值的单位，只有>0的时候的最小的单位，具体看toString方法
         *
         * 根据注释，两个一样的作用，两种一个样的精度丢失，一样的操作  elapsed(单位) elapsed.toxxx()
         * elapsed(TimeUnit.MINUTES))可以设置单位, 但是可能丢失精度，因为如果中间经过100ms，这里单位是s，则结果永远为0，设置正确的单位就不会丢失了
         * elapsed() 返回Duration，里面包含了s和ns，注释里面说没有精度丢失，但是其实还是有精度的丢失的，例子同上，但是可以拿到不同单位的值
         *
         * reset 重置stopWatch，后面可以继续使用
         */
        LOGGER.info("start process the order [{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(100);

        // LOGGER.info("The orderNo [{}] process successful and elapsed [{}] min.",
        //         orderNo, stopwatch.stop().elapsed(TimeUnit.MINUTES));

        LOGGER.info("The orderNo [{}] process successful and elapsed [{}] min.",
                        orderNo, stopwatch.stop().elapsed().toNanos());
    }
}
