package com.leesin.guava.第1讲_工具类;

import com.google.common.base.CharMatcher;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class Test {
    String ha;
    String a;
    public static void main(String[] args) throws IOException {
        List<String> strings = Splitter.on("|").splitToList("leesin|dong");
        System.out.println(strings);
        assertThat(strings, notNullValue());
        List<String> strings1 = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("leesin | dong     ||");
        System.out.println(strings1);

        Iterable<String> split = Splitter.fixedLength(2).split("leesin|dong");
        System.out.println(split);

        List<String> strings2 = Splitter.on("|").limit(3).splitToList("lees|in|don|g");
        System.out.println(strings2);

        Splitter.onPattern("\\|").splitToList("leesin|dong");
        Splitter.on(Pattern.compile("\\|")).splitToList("leesin|dong");

        Map<String, String> split1 = Splitter.on("|").withKeyValueSeparator("=").split("leesin=dong|xiao=ming");
        System.out.println(split1);

        Charset charset = Charset.forName("UTF-8");
        assertThat(charset, is(Charsets.UTF_8));

        assertThat(CharMatcher.javaDigit().matches('8'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));

        assertThat(CharMatcher.is('a').countIn("aas"), equalTo(2));
        assertThat(CharMatcher.breakingWhitespace()
                .collapseFrom("   hah  a", '$'), equalTo("$hah$a"));


        assertThat(CharMatcher.javaDigit().retainFrom("123aaa"),
                equalTo("123"));
        assertThat(CharMatcher.javaDigit().removeFrom("123aaa"),
                equalTo("aaa"));

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("ha", a)
                .add("a", a)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Test test = (Test) o;
        return Objects.equal(ha, test.ha) && Objects.equal(a, test.a);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ha, a);
    }



}
