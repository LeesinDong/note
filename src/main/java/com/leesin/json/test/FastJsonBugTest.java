package com.leesin.json.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * @author Leesin Dong
 * @since Created in 2021/8/17 9:52 上午
 */
public class FastJsonBugTest {
    public static void main(String[] args) {
        String json = "[{\"a\":1}]";
        List<Apple> apples = JSON.parseObject(json, new TypeReference<List<Apple>>() {}.getType());
        List<Apple> apples1 = JSON.parseObject(json, List.class);
        System.out.println(apples);
        System.out.println(apples1);

        // Apple apple = new Apple(1);
        // List<Apple> apples = Arrays.asList(apple);
        // // List<Integer> apples = Arrays.asList(1);
        // System.out.println(apples.toString());
        // System.out.println(JSON.toJSONString(apples, SerializerFeature.WriteMapNullValue));
    }

    public static class Apple{
        int a;
        public Apple(int a) {
            this.a = a;
        }
    }
}
