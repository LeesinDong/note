package com.leesin.java8.wangwenjun.第16讲_Date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2015, 1, 2);
        LocalTime localTime = LocalTime.of(23, 1, 2);


        String format = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format);

        String date = "2021-01-02";
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(parse);

    }
}
