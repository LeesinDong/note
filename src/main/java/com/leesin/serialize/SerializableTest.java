package com.leesin.serialize;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Leesin Dong
 * @since Created in 2021/8/25 11:11 上午
 */
public class SerializableTest {

    /**
     * 内部属性需要序列化
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        serializeAnimal();
    }

    static class Student implements Serializable {
        private String name;
        private int age;
        /**
         * 1 每个内部字段都需要实现序列化 ：Color 类也是需要实现序列化接口的。
         */
        private Color color;//这里如果没有实现序列化接口，那么在 Student 对象序列化时将会报错

        public Student(String name, int age, Color color) {
            this.name = name;
            this.age = age;
            this.color = color;
        }
    }

    /**
     * 2 被序列化的子类不需要实现序列化
     */
    static class SonSerializable extends Student {
        public SonSerializable(String name, int age, Color color) {
            super(name, age, color);
        }
    }

    static class Color implements Serializable{
        public Color() {

        }
    }

    private static void serializeAnimal () throws Exception {
        Color color = new Color();
        Student student = new Student("a", 2, color);
        System.out.println("序列化前：" + student.toString());
        System.out.println("=================开始序列化================");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(student);

        SonSerializable sonSerializable = new SonSerializable("a", 2, color);
        oos.writeObject(sonSerializable);
        oos.flush();
        oos.close();
    }
}
