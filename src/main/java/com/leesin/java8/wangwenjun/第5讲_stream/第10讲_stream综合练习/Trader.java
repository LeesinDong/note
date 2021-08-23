package com.leesin.java8.wangwenjun.第5讲_stream.第10讲_stream综合练习;

/**
 * Created by wangwenjun on 2016/10/22.
 */
public class Trader{

    private final String name;
    private final String city;

    public Trader(String n, String c){
        this.name = n;
        this.city = c;
    }
    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public String toString(){
        return "Trader:"+this.name + " in " + this.city;
    }
}