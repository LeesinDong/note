package com.leesin.guava.第4讲_File;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/14
 * @QQ: 532500648
 ***************************************/
public class CloserTest {

    // cr 这里相当于 try-with-resource， 只不过，try 是为了获取try里面的，这里是catch里面的

    @Test
    public void testCloser() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\files.PNG"));
        Closer closer = Closer.create();
        try {
            // cr closer.register()
            InputStream inputStream = closer.register(byteSource.openStream());
        } catch (Throwable e) {
            /**
             * rethrow会将当前的e保存在thrown
             */
            // cr closer.rethrow()
            throw closer.rethrow(e);
        } finally {
            /**
             * close的时候拿到thrown，addSuppressed到父异常
             */
            // cr closer.close()
            closer.close();
        }
    }

    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        try {

            System.out.println("work area.");
            throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("exception area");
            throw new RuntimeException();
        } finally {
            /**
             * 因为抛出异常的执行顺序是：try catch中的sout finally catch中的throw
             * 但是如果finally中throw异常，会导致catch中的throw被压制住了，丢失了
             * Closer就是用来解决这个问题
             */
            System.out.println("finally area");
        }
    }

    /**
     * 通过源码上面的finally中的close，相当于这里面finally里面的内容
     */
    @Test
    public void testTryCatch() {
        Throwable t = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            try {
                //close
                throw new RuntimeException("2");
            } catch (Exception e) {
                /**
                 * finally中抛出异常的时候，把catch（外面的）中的异常添加到异常的list中，最终会抛出两个异常 1 2
                 */
                t.addSuppressed(e);
            }
        }
    }
}
