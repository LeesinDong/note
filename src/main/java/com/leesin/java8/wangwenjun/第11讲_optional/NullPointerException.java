package com.leesin.java8.wangwenjun.第11讲_optional;

import com.leesin.java8.wangwenjun.Person;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/24 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class NullPointerException {

    /**
     * 在effective java中讲到：
     * 方法尽量不要返回空的，比如返回空字符串或者List，这样调用方便可以规避
     * 但是如果返回的是对象，那么不能限制里面的字段的属性是否为空，被调动方还是需要进行判断
     * optional就是为了解决这个问题。
     */

    public static void main(String[] args) {
       String insuranceName = getInsuranceName(new Person());
       String result = getInsuranceNameByDeepDoubts(new Person());
       System.out.println(result);

    }

    private static String getInsuranceNameByDeepDoubts(Person person) {
        // 问题代码：
        /*if (null != person) {
            Car car = person.getCar();
            if (null != car) {
                Insurance insurance = car.getInsurance();
                if (null != insurance) {
                    return insurance.getName();
                }
            }
        }*/

        return "UNKNOWN";
    }

    private static String getInsuranceNameByMultExit(Person person)
    {
        // 问题代码：卫语句的方式依然啰嗦
        /*final String defaultValue = "UNKNOWN";
        if (null == person) {
            return defaultValue;
        }
        Car car = person.getCar();
        if (null == car) {
            return defaultValue;
        }
        Insurance insurance = car.getInsurance();
        if (null == insurance) {
            return defaultValue;
        }

        return insurance.getName();*/
        return null;
    }

    private static String getInsuranceName(Person person) {
        // 可能空指针的代码
        // return person.getCar().getInsurance().getName();
        return null;
    }
}
