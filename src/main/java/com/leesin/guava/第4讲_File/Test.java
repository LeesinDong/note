package com.leesin.guava.第4讲_File;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.apache.curator.shaded.com.google.common.io.BaseEncoding;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Test {
    public static void main(String[] args) throws IOException {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(baseEncoding.encode("lessin".getBytes()));
        BaseEncoding baseEncoding1 = BaseEncoding.base64();
        System.out.println(new String(baseEncoding1.decode("bGVzc2lu")));;

        ByteSource byteSource = ByteSource.wrap(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
        ByteSource slice = byteSource.slice(1, 5);
        byte[] read = slice.read();
        for (byte b : read) {
            System.out.println(b);
        }

    }

    public void test() throws IOException {
        File file = new File("");
        ByteSink byteSink = Files.asByteSink(file);
        byte[] bytes =new byte[]{0x01};
        byteSink.write(bytes);

        byte[] bytes1 = Files.toByteArray(file);

        Files.asByteSource(file).copyTo(Files.asByteSink(file));
        Files.asByteSource(file).hash(Hashing.sha256()).toString();

        CharSource charSource = Files.asCharSource(file, StandardCharsets.UTF_8);
        ImmutableList<String> strings = charSource.readLines();

        Closer closer = Closer.create();
        try {
            OutputStream register = closer.register(byteSink.openStream());
        } catch (Exception e) {
            System.out.println("a");
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }

        File sourceFile = new File("");
        File targetFile = new File("");

        Files.copy(sourceFile, targetFile);
        Files.move(sourceFile, targetFile);

        List<String> strings1 = Files.readLines(file, Charsets.UTF_8);

        LineProcessor lineProcessor = new LineProcessor() {

            List<String> result = new ArrayList<>();

            @Override
            public boolean processLine(String line) throws IOException {
                if (line.length() == 0) {
                    return false;
                }
                result.add(line);
                return true;
            }

            @Override
            public Object getResult() {
                return result;
            }
        };
        Files.asCharSource(file, Charsets.UTF_8).readLines(lineProcessor);

        Files.asByteSource(file).hash(Hashing.sha256());
        Files.asCharSink(file, Charsets.UTF_8).write("");
        String read = Files.asCharSource(file, Charsets.UTF_8).read();
        Files.touch(file);

        Files.fileTreeTraverser().preOrderTraversal(file);
        Files.fileTreeTraverser().postOrderTraversal(file);
        Files.fileTreeTraverser().children(file);
    }
}
