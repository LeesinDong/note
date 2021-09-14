package com.leesin.java8.wangwenjun.第11讲_optional;

import java.util.Optional;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/25 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class OptionalInAction {

    /**
     * optional的编程理念：you just tell “how”, not “do what” 只需要在意怎么做，而不需要在意做什么（if xxx != null xxx）
     * @param args
     */
    public static void main(String[] args) {
        Optional.ofNullable(getInsuranceNameByOptional(null)).ifPresent(System.out::println);
    }

    private static String getInsuranceNameByOptional(Person person) {

        /**
         * cr optional map flatMap的本质区别：【返回值】
         *  1 Map apply完了之后包装了一层optional
         *  2 flatMap apply完了之后返回值就是Optional则直接返回，没有再包装
         *
         *  如何判断？
         *  xxx.getxxx方法的返回值是普通类则用Map，是Optional则用flatMap
         *
         *  更多参看源码即可
         */
        // 下面会报错
        // Optional.ofNullable(person)
        //         .map(Person::getCar)
        //         .map(Car::getInsurance)
        //         .map(Insurance::getName).orElse("Unknown");
        // 因为：返回值是Optional<Optional<Car>>, 而不是Optional<Car>,所以optional里面的value是Optional<Car> ,所以不能map(Car::getInsurance)
        Optional<Optional<Car>> car = Optional.ofNullable(person)
                .map(Person::getCar);

        return Optional.ofNullable(person)
                .flatMap(Person::getCar).flatMap(Car::getInsurance)
                .map(Insurance::getName).orElse("Unknown");
    }
}
