package com.leesin.guava.第1讲_工具类;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/7
 * @QQ: 532500648
 ***************************************/
public class StringsTest {

    @Test
    public void testStringsMethod() {
        // 空字符串返回null
        assertThat(Strings.emptyToNull(""), nullValue());
        // null返回空字符串
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
        // 共同前缀
        assertThat(Strings.commonPrefix("Hello", "Hit"), equalTo("H"));
        assertThat(Strings.commonPrefix("Hello", "Xit"), equalTo(""));
        // 共同后缀
        assertThat(Strings.commonSuffix("Hello", "Echo"), equalTo("o"));

        // 重复字符串
        assertThat(Strings.repeat("Alex", 3), equalTo("AlexAlexAlex"));

        // 是Null或Empty
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));

        // 字符串不够长度，则从前面补齐
        assertThat(Strings.padStart("Alex", 3, 'H'), equalTo("Alex"));
        assertThat(Strings.padStart("Alex", 5, 'H'), equalTo("HAlex"));
        // 字符串不够长度，则从后面补齐
        assertThat(Strings.padEnd("Alex", 5, 'H'), equalTo("AlexH"));
    }

    // cr  Charset 字符类型
    @Test
    public void testCharsets() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8, equalTo(charset));
    }

    // cr CharMatcher 匹配
    /**
     * functor
     */
    @Test
    public void testCharMatcher() {
        /**
         * javaDigit() 数字类型
         */
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));


        /**
         * is('A').countIn A在里面的数量
         */
        assertThat(CharMatcher.is('A').countIn("Alex Sharing the Google Guava to Us"), equalTo(1));
        /**
         * breakingWhitespace().collapseFrom 替换空白符为 *
         */
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("      hello Guava     ", '*'), equalTo("*hello*Guava*"));
        /**
         * 数字或者空白符则删除
         * 数字或空白符则保留
         */
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"), equalTo("helloworld"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"), equalTo(" 234 "));
    }

    public Integer text(){
        return 0;
    }
}
