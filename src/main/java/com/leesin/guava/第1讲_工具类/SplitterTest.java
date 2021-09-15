package com.leesin.guava.第1讲_工具类;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/7
 * @QQ: 532500648
 ***************************************/
public class SplitterTest {

    // cr 分割 按照字符   Splitter.on("|").splitToList
    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("hello|world");
        Splitter.on("|").splitToList("hello | world");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }
    @Test
    public void testSplit_On_Split_OmitEmpty() {
        List<String> result = Splitter.on("|").splitToList("hello|world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(5));
        /**
         * omitEmptyStrings 忽略 | 左右有空的
         */
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
    }
    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello "));
        assertThat(result.get(1), equalTo(" world"));
        /**
         * trimResults 对结果进行trim, trimResults().omitEmptyStrings() 前后顺序可以发生变化
         */
        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }


    // cr 分割 按照长度
    /**
     * aaaabbbbccccdddd
     */
    @Test
    public void testSplitFixLength() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");

        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0), equalTo("aaaa"));
        assertThat(result.get(3), equalTo("dddd"));
    }

    // cr 分割 按照字符 + 个数
    // 这里的limit是分成几份的意思，会将后面所有的包括分隔符放在最后一个
    @Test
    public void testSplitOnSplitLimit() {
        List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java#google#scala"));
    }


    // cr 分割 按照正则字符串
    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    // cr 分割 按照正则Pattern
    @Test
    public void testSplitOnPattern() {
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }


    // cr 分割形成Map withKeyValueSeparator
    @Test
    public void testSplitOnSplitToMap() {
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).trimResults()
                .omitEmptyStrings().withKeyValueSeparator("=").split("hello=HELLO| world=WORLD|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("hello"),equalTo("HELLO"));
        assertThat(result.get("world"),equalTo("WORLD"));

    }
}
