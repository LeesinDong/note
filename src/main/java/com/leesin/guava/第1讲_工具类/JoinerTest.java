package com.leesin.guava.第1讲_工具类;


import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/6
 * @QQ: 532500648
 ***************************************/
public class JoinerTest {
    // cr 本质：Joiner.on("#").join(xxxList);  Joiner.on("#").append(appender, xxxList);
    //         Joiner.on("#").withKeyValueSeparator("=").join(xxxMap); Joiner.on("#").withKeyValueSeparator("=").append
    //         (appender, xxxMap);



    private final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );

    private final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );

    // cr  ImmutableMap.of
    private final Map<String, String> stringMap = ImmutableMap.of("Hello", "Guava", "Java", "Scala");

    private final String targetFileName = "G:\\Teaching\\汪文君Google Guava实战视频\\guava-joiner.txt";
    private final String targetFileNameToMap = "G:\\Teaching\\汪文君Google Guava实战视频\\guava-joiner-map.txt";

    // cr join常见
    /**
     * join 没有null              Joiner.on("#").join(stringList);
     */
    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        // cr asset equalTo
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));

    }
    /**
     * join 有null 抛出空指针
     */
    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }
    /**
     * join 有null 跳过        skipNulls
     */
    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    /**
     * join 有null 默认值       useForNull
     */
    @Test
    public void testJoin_On_Join_WithNullValue_UseDefaultValue() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));

    }

    // cr join append
    /**
     * appendTo StrinBuilder
     */
    @Test
    public void testJoin_On_Append_To_StringBuilder() {
        // 这里会追加到后面而不是把前面的覆盖掉
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, stringListWithNullValue);
        // cr sameInstance
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
        assertThat(builder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));

    }
    /**
     * appendTo writer
     */
    @Test
    public void testJoin_On_Append_To_Writer() {
        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }

    // cr join Map
    /**
     * join Map 返回字符串
     */
    @Test
    public void testJoinOnWithMap() {
        assertThat(Joiner.on('#').withKeyValueSeparator("=").join(stringMap),
                equalTo("Hello=Guava#Java=Scala"));
    }

    /**
     * Map appendTo
     */
    @Test
    public void testJoinOnWithMapToAppendable() {
        try (FileWriter writer = new FileWriter(new File(targetFileNameToMap))) {
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }

    }

    // cr java8
    /**
     * Java8 stream 模拟 joiner
     */
    @Test
    public void testJoiningByStreamSkipNullValues() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty())
                .collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }
    @Test
    public void testJoiningByStreamWithDefaultValue() {
        String result = stringListWithNullValue.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }
    private String defaultValue(final String item) {
        return item == null || item.isEmpty() ? "DEFAULT" : item;
    }

}

