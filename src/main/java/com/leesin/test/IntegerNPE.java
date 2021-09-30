package com.leesin.test;

/**
 * @author Leesin Dong
 * @since Created in 2021/7/2 9:05 下午
 */
public class IntegerNPE {
    public static void main(String[] args) {
        Integer a = null;
        testInt(a);
        testInteger(a);
        testString(null);
    }

    /**
     * int null NPE 自动拆箱导致
     * @param value
     */
    public static void testInt(int value) {
    }

    /**
     * Integer null 不会 NPE
     * @param value
     */
    public static void testInteger(Integer value) {
    }

    /**
     * String null 不会 NPE
     * @param value
     */
    public static void testString(String value) {
    }
}
