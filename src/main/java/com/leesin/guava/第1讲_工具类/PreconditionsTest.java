package com.leesin.guava.第1讲_工具类;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.fail;


/***************************************
 * @author:Alex Wang
 * @Date:2017/10/7
 * @QQ: 532500648
 ***************************************/

/**
 * Precondition 本质：断言
 * Objects
 * assert key word(statement)
 */
public class PreconditionsTest {
    // cr  以后所有的if else 都能用这个代替，能不能用这个代替要看项目中的异常handler对于其他的Exception是怎么处理的？是否能返回应有的Exception的Message
    //    如果是 result.setCode(e.getMessage());，就是可以用的   https://www.ktanx.com/blog/p/5088

    // cr Preconditions 本质：断言   4个：checkNotNull\checkArguments\checkState\checkElementIndex


    // cr Preconditions.checkNotNull
    /**
    * 判空
    */
    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        Preconditions.checkNotNull(null);
    }
    /**
    * 判空 + message
    */
    @Test
    public void testCheckNotNullWithMessage() {
        try {
            checkNotNullWithMessage(null);
        } catch (Exception e) {
            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should not be null"));
        }
    }
    /**
     * 判空 + format
     */
    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            checkNotNullWithFormatMessage(null);
        } catch (Exception e) {
            assertThat(e, is(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should not be null and the size must be 2"));
        }
    }

    // cr Preconditions.checkArgument、checkState 本质一样，只不过语义不一样，和checkNotNull一样，有message、format
    //  checkState更倾向于状态判断：线程、线程池、状态机
    @Test
    public void testCheckArguments() {
        final String type = "A";
        try {
            Preconditions.checkArgument(type.equals("B"));
        } catch (Exception e) {
            assertThat(e, is(IllegalArgumentException.class));
        }
    }
    @Test
    public void testCheckState() {
        try {
            final String state = "A";
            Preconditions.checkState(state.equals("B"), "The state is illegal.");
            fail("should not process to here.");
        } catch (Exception e) {
            assertThat(e, is(IllegalStateException.class));
        }
    }

    // cr Preconditions.checkIndex 当前数组是否数组越界（index，数组大小）
    @Test
    public void testCheckIndex() {
        try {
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10, list.size());
        } catch (Exception e) {
            assertThat(e, is(IndexOutOfBoundsException.class));
        }
    }

    // cr 相同功能 java8、assert中实现， checkNotNull ：java8不好，用guava
    @Test(expected = NullPointerException.class)
    public void testByObjects() {
        /**
         * 同样有message format
         */
        Objects.requireNonNull(null);
    }

    @Test(expected = AssertionError.class)
    public void testAssert() {
        List<String> list = null;
        assert list != null;
    }
    @Test
    public void testAssertWithMessage() {
        try {
            List<String> list = null;
            /**
             * list == null 则 后面的message
             */
            assert list != null : "The list should not be null.";
        } catch (Error e) {
            assertThat(e, is(AssertionError.class));
            assertThat(e.getMessage(), equalTo("The list should not be null."));
        }
    }

    private void checkNotNull(final List<String> list) {
        Preconditions.checkNotNull(list);
    }

    private void checkNotNullWithMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "The list should not be null");
    }

    private void checkNotNullWithFormatMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "The list should not be null and the size must be %s", 2);
    }
}
