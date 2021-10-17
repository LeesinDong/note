package com.leesin.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Leesin Dong
 * @since Created in 2021/10/9 3:40 下午
 */
@Slf4j
public class Test {
    public static int num=1;
    public static void main(String[] args)  {
        int result;
        result = num();
        System.out.println(result);//输出结果为1003
        System.out.println(num);//输出结果为1001
    }
    private static int num() {
        try{
            int b=4/0;
            System.out.println(111);
            System.out.println(111);
            System.out.println(111);
            return num = num+1000;
        }catch(Exception e){
            // return num = num+1000;
        }finally {
            return num+2;
        }
    }
}
