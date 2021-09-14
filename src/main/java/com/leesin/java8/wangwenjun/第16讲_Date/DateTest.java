package com.leesin.java8.wangwenjun.第16讲_Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/13 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class DateTest {
    public static void main(String[] args) throws ParseException, InterruptedException {
        // Date date = new SimpleDateFormat(116, 2, 18);
        // System.out.println(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < 30; i++) {
            /**
             * 多线程的情况下会抛出异常 mutpile point
             */
            new Thread(() -> {
                for (int x = 0; x < 100; x++) {
                    Date parseDate = null;
                    try {
                        parseDate = sdf.parse("20160505");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(parseDate);
                }
            }). start();
        }

       testLocalDate();
       testLocalTime();
       combineLocalDateAndTime();
       testInstant();
       testDuration();
       testPeriod();
       testDateFormat();
        testDateParse();
    }

    // cr LocalDate、LocalTime、LocalDateTime、Instant
    private static void testLocalDate() {
        LocalDate localDate = LocalDate.of(2016, 11, 13);
        System.out.println(localDate.getYear());
        // 英文
        System.out.println(localDate.getMonth());
        // 数字
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfWeek());

        // cr  + 1 天
        System.out.println(localDate.plusDays(1));
        // cr - 1 天
        System.out.println(localDate.minusDays(1));


        localDate.get(ChronoField.DAY_OF_MONTH);
    }

    private static void testLocalTime() {
        LocalTime time = LocalTime.now();
        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
    }

    private static void combineLocalDateAndTime() {
        LocalDate localDate = LocalDate.now();
        LocalTime time = LocalTime.now();
        /**
         * 组合 LocalDate LocalTime
         */
        LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
        System.out.println(localDateTime.toString());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    private static void testInstant() throws InterruptedException {
        /**
         * 点
         */
        Instant start = Instant.now();
        Thread.sleep(1000L);
        Instant end = Instant.now();
        /**
         * 两个点生成段
         */
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }

    // cr Duration、Period
    private static void testDuration() {
        LocalTime time = LocalTime.now();
        // 减去一小时
        LocalTime beforeTime = time.minusHours(1);
        /**
         * 两个点生成段
         */
        Duration duration = Duration.between(time, beforeTime);
        System.out.println(duration.toHours());
    }

    /**
     * Period 时代，用于时间跨度大的 时间段, 本质和duration一样
     */
    private static void testPeriod() {
        Period period = Period.between(LocalDate.of(2014, 1, 10), LocalDate.of(2016, 1, 10));
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
        System.out.println(period.getYears());
    }

    // cr format、parse
    /**
     * format
     */
    private static void testDateFormat() {
        /**
         * DateTimFormatter
         */
        LocalDate localDate = LocalDate.now();
        String format1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
//        String format2 = localDate.format(DateTimeFormatter.ISO_LOCAL_TIME);
        System.out.println(format1);
//        System.out.println(format2);

        /**
         * 手写formatter
         */
        DateTimeFormatter mySelfFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = localDate.format(mySelfFormatter);
        System.out.println(format);
    }

    /**
     * parse
     */
    private static void testDateParse() {
        String date1 = "20161113";
        LocalDate localDate = LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(localDate);
        
        DateTimeFormatter mySelfFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date2 = "2016-11-13";
        LocalDate localDate2 = LocalDate.parse(date2, mySelfFormatter);
        System.out.println(localDate2);
    }
}