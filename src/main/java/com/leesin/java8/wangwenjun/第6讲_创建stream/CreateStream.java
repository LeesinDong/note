package com.leesin.java8.wangwenjun.第6讲_创建stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by wangwenjun on 2016/10/19.
 */
public class CreateStream {

    public static void main(String[] args) {
        /*createStreamFromCollection().forEach(System.out::println);
        createStreamFromValues().forEach(System.out::println);*/
//        createStreamFromArrays().forEach(System.out::println);

        /*Stream<String> streamFromFile = createStreamFromFile();
        System.out.println(streamFromFile);
        streamFromFile.forEach(System.out::println);*/

//        createStreamFromIterator().forEach(System.out::println);
//        createStreamFromGenerate().forEach(System.out::println);
        createObjStreamFromGenerate().forEach(System.out::println);
    }


    /**
     * Generate the stream object from collection.
     * 创建stream的三种方式
     */
    /**
     * 1 list.stream()
     */
    private static Stream<String> createStreamFromCollection() {
        /**list里面的顺序，是不会变的，当然用了sort方法等除外*/
        List<String> list = Arrays.asList("hello", "alex", "wangwenjun", "world", "stream");
        return list.stream();
    }

    /**
     * 2 Stream.of()
     */
    private static Stream<String> createStreamFromValues() {
        return Stream.of("hello", "alex", "wangwenjun", "world", "stream");
    }

    /**
     * 3 Arrays.stream(new String[])
     */
    private static Stream<String> createStreamFromArrays() {
        String[] strings = {"hello", "alex", "wangwenjun", "world", "stream"};
        return Arrays.stream(strings);
    }

    /**
     * 4 stream方式读取文件
     */
    private static Stream<String> createStreamFromFile() {
        Path path = Paths.get("C:\\Users\\wangwenjun\\IdeaProjects\\java8\\java8-sharing\\src\\main\\java\\com\\wangwenjun\\java8\\CreateStream.java");
        // 重要的就是这个files.line
        try (Stream<String> streamFromFile = Files.lines(path)) {
            streamFromFile.forEach(System.out::println);
            return streamFromFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 5 Stream.iterate()
     */
    private static Stream<Integer> createStreamFromIterator() {
        // 每次叠加： 这里iterate会无限的创建，一直循环下去：2 4 6 8，所以limit10，只输出10个
        Stream<Integer> stream = Stream.iterate(0, n -> n + 2).limit(10);
        return stream;
    }

    /**
     * 6 Stream.generate
     */
    private static Stream<Double> createStreamFromGenerate() {
        // 每次通过相同的函数返回值 无限创建元素，所以这里也limit
        return Stream.generate(Math::random).limit(10);
    }

    /**
     * 自定义一个generate中supplier
     */
    private static Stream<Obj> createObjStreamFromGenerate() {
        return Stream.generate(new ObjSupplier()).limit(10);
    }
    /**
     * 定义有一个supplier
     */
    static class ObjSupplier implements Supplier<Obj> {
        private int index = 0;
        private Random random = new Random(System.currentTimeMillis());
        @Override
        public Obj get() {
            index = random.nextInt(100);
            return new Obj(index, "Name->" + index);
        }
    }
    /**
     * 定义一个对象
     */
    static class Obj {
        private int id;
        private String name;

        public Obj(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
