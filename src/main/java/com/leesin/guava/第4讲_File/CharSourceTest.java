package com.leesin.guava.第4讲_File;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/14
 * @QQ: 532500648
 ***************************************/
public class CharSourceTest {

    @Test
    public void testCharSourceWrap() throws IOException {
        /**
         * charSource 本质就是字符流，source输入流 = reader
         * charSink 本质是字符流，sink输出流 = writer
         *
         * byteSource 本质是字节流，source输入流 = reader
         * byteSink 本质是字节流，sink输出流 = writer
         */

        // 无法通过文件直接获取字符流CharSource
        /**
         * 获取字符流1
         */
        CharSource charSource = CharSource.wrap("i am the CharSource");
        /**
         * 获取字符流2，本质获取字节流再转化为字符流
         */
        Files.asCharSource(new File(""), Charsets.UTF_8);

        String resultAsRead = charSource.read();
        assertThat(resultAsRead, equalTo("i am the CharSource"));

        ImmutableList<String> list = charSource.readLines();
        assertThat(list.size(), equalTo(1));

        assertThat(charSource.length(), equalTo(19L));

        assertThat(charSource.isEmpty(), equalTo(false));

        assertThat(charSource.lengthIfKnown().get(), equalTo(19L));
    }

    @Test
    public void testConcat() throws IOException {
        /**
         * 组合，把多个reader组合成一个
         * 实现：读取的时候，多个reader一个一个读取，是否往下接着读取，看next是否还有，具体看
         * com.google.common.io.CharSource.ConcatenatedCharSource
         * {@link com.google.common.io.MultiReader#advance()} --- 先关闭当前的流，后面有流的话，打开下一个继续处理。
         */
        CharSource charSource = CharSource.concat(
                CharSource.wrap("i am the CharSource1\n"),
                CharSource.wrap("i am the CharSource2")
        );

        System.out.println(charSource.readLines().size());
        charSource.lines().forEach(System.out::println);
    }
}
