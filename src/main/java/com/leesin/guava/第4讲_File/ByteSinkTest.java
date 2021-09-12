package com.leesin.guava.第4讲_File;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/14
 * @QQ: 532500648
 ***************************************/
public class ByteSinkTest {

    private final String path = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\ByteSinkTest.dat";

    @Test
    public void testByteSink() throws IOException {
        File file = new File(path);
        file.deleteOnExit();
        /**
         * Files.asByteSink
         */
        ByteSink byteSink = Files.asByteSink(file);
        byte[] bytes = {0x01, 0x02};
        byteSink.write(bytes);

        /**
         * Files.toByteArray
         */
        byte[] expected = Files.toByteArray(file);

        assertThat(expected, is(bytes));
    }

    @Test
    public void testFromSourceToSink() throws IOException {
        String source = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\files.PNG";
        String target = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\files-2.PNG";
        File sourceFile = new File(source);
        File targetFile = new File(target);
        targetFile.deleteOnExit();
        /**
         * Files.asByteSource
         */
        ByteSource byteSource = Files.asByteSource(sourceFile);
        /**
         * 输入流 拷贝到 输出流
         */
        byteSource.copyTo(Files.asByteSink(targetFile));

        assertThat(targetFile.exists(), equalTo(true));

        /**
         * Files.asByteSource(sourceFile).hash(Hashing.sha256()
         */
        assertThat(
                Files.asByteSource(sourceFile).hash(Hashing.sha256()).toString(),
                equalTo(Files.asByteSource(targetFile).hash(Hashing.sha256()).toString())
        );


    }
}
