package com.leesin.guava.第4讲_File;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2017/10/15
 * @QQ: 532500648
 ***************************************/
public class BaseEncodingTest {
    @Test
    public void testBase64Encode() {
        /**
         * cr encode
         */
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(baseEncoding.encode("hello".getBytes()));
        System.out.println(baseEncoding.encode("a".getBytes()));
    }

    @Test
    public void testBase64Decode() {
        /**
         * cr decode
         */
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(new String(baseEncoding.decode("aGVsbG8=")));
    }

    @Test
    public void testMyBase64Encode() {
        System.out.println(Base64_略.encode("hello"));
        assertThat(Base64_略.encode("hello"), equalTo(BaseEncoding.base64().encode("hello".getBytes())));
        assertThat(Base64_略.encode("alex"), equalTo(BaseEncoding.base64().encode("alex".getBytes())));
        assertThat(Base64_略.encode("wangwenjun"), equalTo(BaseEncoding.base64().encode("wangwenjun".getBytes())));
        assertThat(Base64_略.encode("scala"), equalTo(BaseEncoding.base64().encode("scala".getBytes())));
        assertThat(Base64_略.encode("kafka"), equalTo(BaseEncoding.base64().encode("kafka".getBytes())));
    }
}
