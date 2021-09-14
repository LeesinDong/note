package com.leesin.guava.第8讲_collections;

import com.google.common.collect.*;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
        ImmutableList.builder().add(1)
                .build();

        ArrayList<Integer> list =
                Lists.newArrayList(1, 2);

        Lists.transform(list, e -> e.toString());
        List<List<Integer>> partition = Lists.partition(list, 10);

        HashBiMap<Object, Object> objectObjectHashBiMap = HashBiMap.create();
        objectObjectHashBiMap.put(1, 2);

        objectObjectHashBiMap.inverse();
        objectObjectHashBiMap.forcePut(2,2);

        ArrayList<Integer> list1 = Lists.newArrayList(1, 2, 3);
        ImmutableMap<String, Integer> stringIntegerImmutableMap = Maps.uniqueIndex(list1, i -> i + "1");
        System.out.println(stringIntegerImmutableMap);
        Map<Integer, String> integerStringMap = Maps.asMap(Sets.newHashSet(1, 2, 3), i -> i + "a");
        System.out.println(integerStringMap);

        LinkedListMultimap<Object, Object> objectObjectLinkedListMultimap = LinkedListMultimap.create();
        objectObjectLinkedListMultimap.put(1, 2);
        objectObjectLinkedListMultimap.put(1, 3);
        List<Object> objects = objectObjectLinkedListMultimap.get(1);
        System.out.println(objects);

        ArrayList<Integer> list2 = Lists.newArrayList(1, 2, 3, null);
        Collections.sort(list2, Ordering.natural().nullsLast());
        System.out.println(list2);

        HashBasedTable<Object, Object, Object> tatt = HashBasedTable.create();
        tatt.put(1, 2, 3);

        Set<Table.Cell<Object, Object, Object>> cells = tatt.cellSet();

    }
}
