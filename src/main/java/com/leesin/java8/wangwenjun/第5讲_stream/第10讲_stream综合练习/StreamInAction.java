package com.leesin.java8.wangwenjun.第5讲_stream.第10讲_stream综合练习;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by wangwenjun on 2016/10/22.
 */
public class StreamInAction {
    public static void main(String[] args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


        //1. Find all transactions in the year 2011 and sort them by value (small to high).
        // 查找2011年的所有交易，并按价值排序(从小到高)。
        List<Transaction> result = transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(result);

        //2.What are all the unique cities where the traders work?
        // 哪些是商人工作的独特城市?
        System.out.println("====");
        transactions.stream().map(t -> t.getTrader().getCity())
                .distinct().forEach(System.out::println);
        System.out.println("====");


        //3.Find all traders from Cambridge and sort them by name.
        // 3. 找出所有来自剑桥(城市)的交易者并按姓名排序。
        System.out.println("11111");
        transactions.stream().map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
        System.out.println("11111");
        System.out.println("11111");

        //4.Return a string of all traders’ names sorted alphabetically
        // 返回按字母顺序排列的所有交易员姓名的字符串
        String value = transactions.stream().map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                /**
                 * reduce可以用来字符串拼接
                 */
                .reduce("", (name1, name2) -> name1 + name2);
        System.out.println(value);

        //5. Are any traders based in Milan?
        // 是否有traders是米兰的
        boolean liveInMilan1 = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        boolean liveInMilan2 = transactions.stream().map(Transaction::getTrader).anyMatch(t -> t.getCity().equals("Milan"));
        System.out.println(liveInMilan1);
        System.out.println(liveInMilan2);

        //6.Print all transactions’ values from the traders living in Cambridge.
        // 交易员在剑桥的的交易
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);


        //7.What’s the highest value of all the transactions?
        // 找出最高的交易
        /**
         * 最大的首先想到reduce
         */
        Optional<Integer> maxValue = transactions.stream().map(Transaction::getValue).reduce((i, j) -> i > j ? i : j);
        System.out.println(maxValue.get());

        //8.Find the transaction with the smallest value.
        // 最小的
        Optional<Integer> minValue = transactions.stream().map(Transaction::getValue).reduce(Integer::min);
        System.out.println(minValue.get());

    }

}
