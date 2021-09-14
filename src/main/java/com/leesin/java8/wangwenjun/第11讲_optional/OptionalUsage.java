package com.leesin.java8.wangwenjun.第11讲_optional;

import java.util.Optional;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/24 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class OptionalUsage {

    public static void main(String[] args) {

        /**
         * cr 1 开头 3
         */
        /**
         * empty
         */
        Optional<Insurance> insuranceOptional = Optional.<Insurance>empty();
       insuranceOptional.get();
        /**
         * of
         */
        Optional<Insurance> insuranceOptional1 = Optional.of(new Insurance());
        insuranceOptional1.get();
        /**
         * ofNullAble是对 empty、of的合并相当于
         */
        Optional<Insurance> objectOptional = Optional.ofNullable(null);


        /**
         * cr 2 中间 2
         */
        /**
         * filter
         */
        Insurance insurance = insuranceOptional1.filter(i -> i.getName() != null).get();
        System.out.println(insurance);
        /**
         * map
         */
        Optional<String> nameOptional = insuranceOptional1.map(i -> i.getName());
        System.out.println(nameOptional.orElse("empty Value"));





        /**
         * cr 3 结尾 4
         */
        /**
         * orElseGet 提供supplier，这里面可以写方法，即前面为空的时候{xxx做一系列的处理}
         */
        objectOptional.orElseGet(Insurance::new);
        /**
         * orElse 提供T
         */
        objectOptional.orElse(new Insurance());
        /**
         * orElseThrow
         */
        objectOptional.orElseThrow(RuntimeException::new);
        objectOptional.orElseThrow(() -> new RuntimeException("Not have reference"));
        /**
         * ifPresent
         */
        System.out.println(nameOptional.isPresent());
        nameOptional.ifPresent(System.out::println);


        System.out.println(getInsuranceName(null));
        System.out.println(getInsuranceNameByOptional(null));
    }


    private static String getInsuranceName(Insurance insurance) {
        if (null == insurance) {
            return "unknown";
        }

        return insurance.getName();
    }

    private static String getInsuranceNameByOptional(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("unknown");
    }
}