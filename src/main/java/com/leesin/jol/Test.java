package com.leesin.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Leesin Dong
 * @since 2022/1/11
 */
public class Test {
    public static void main(String[] args) {
        ClassLayout classLayout = ClassLayout.parseInstance(new ObjA());
        System.out.println(classLayout.toPrintable());

        ClassLayout classLayout2 = ClassLayout.parseInstance(new int[]{});
        System.out.println(classLayout2.toPrintable());

        Object[] arr = new Object[1024];
        ClassLayout classLayout1 = ClassLayout.parseInstance(arr);
        System.out.println(classLayout1.toPrintable());
    }

    private static class ObjA{}
}
