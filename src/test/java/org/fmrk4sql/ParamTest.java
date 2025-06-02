/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024. Alexandr Sysoev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the “Software”), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.fmrk4sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.DblVal;
import org.fmrk4sql.val.IntVal;
import org.fmrk4sql.val.JdVal;
import org.fmrk4sql.val.JdsqlVal;
import org.fmrk4sql.val.LdVal;
import org.fmrk4sql.val.LongVal;
import org.fmrk4sql.val.ObjVal;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for param class.
 * @since 0.1.0
 */
final class ParamTest {
    /**
     * Test name.
     */
    private static final String TEST_NAME = "testName";

    /**
     * Test name.
     */
    private static final String TEST = "test";

    /**
     * Test value.
     * @since 0.1.0
     */
    private static final String VALUE = "value";

    @Test
    void fmParamRenameWithNull() {
        final Param param = new FmParam("table_name", new StrVal("fmrk_table"));
        final NullPointerException exception = Assertions.catchThrowableOfType(
            () -> param.rename(null), NullPointerException.class
        );
        Assertions
            .assertThat(exception)
            .isInstanceOf(NullPointerException.class)
            .hasMessage("name is marked non-null but is null");
    }

    @Test
    void fmParamRename() {
        final Param param = new FmParam("table_name", new StrVal("fmrk_table"));
        final Param expected = new FmParam("new_name", new StrVal("fmrk_table"));
        Assertions
            .assertThat(param.rename("new_name"))
            .isEqualTo(expected);
    }

    @Nested
    @DisplayName("Constructor validation tests")
    final class ConstructorValidationTests {

        @Test
        @DisplayName("Should create valid object with proper name and value")
        void shouldCreateValidObject() {
            final Value expected = new StrVal(ParamTest.TEST);
            final FmParam param = new FmParam(ParamTest.TEST_NAME, expected);
            Assertions.assertThat(param.name()).isEqualTo(ParamTest.TEST_NAME);
            Assertions.assertThat(param.value()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("String constructor tests")
    final class StringConstructorTests {

        @Test
        @DisplayName("Should create FmParam with String value")
        void shouldCreateWithStringValue() {
            final FmParam param = new FmParam("stringParam", "testString");
            Assertions.assertThat(param.name()).isEqualTo("stringParam");
            Assertions.assertThat(param.value()).isInstanceOf(StrVal.class);
        }
    }

    @Nested
    @DisplayName("Numeric constructor tests")
    final class NumericConstructorTests {

        @Test
        @DisplayName("Should create FmParam with Long value")
        void shouldCreateWithLongValue() {
            final FmParam param = new FmParam("longParam", 123L);
            Assertions.assertThat(param.name()).isEqualTo("longParam");
            Assertions.assertThat(param.value()).isInstanceOf(LongVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Integer value")
        void shouldCreateWithIntegerValue() {
            final FmParam param = new FmParam("intParam", 456);
            Assertions.assertThat(param.name()).isEqualTo("intParam");
            Assertions.assertThat(param.value()).isInstanceOf(IntVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Double value")
        void shouldCreateWithDoubleValue() {
            final FmParam param = new FmParam("doubleParam", 123.45);
            Assertions.assertThat(param.name()).isEqualTo("doubleParam");
            Assertions.assertThat(param.value()).isInstanceOf(DblVal.class);
        }
    }

    @Nested
    @DisplayName("Object constructor tests")
    final class ObjectConstructorTests {

        @Test
        @DisplayName("Should create FmParam with Object value")
        void shouldCreateWithObjectValue() {
            final Object obj = new Object();
            final FmParam param = new FmParam("objParam", obj);
            Assertions.assertThat(param.name()).isEqualTo("objParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Iterable value")
        void shouldCreateWithIterableValue() {
            final List<String> list = Arrays.asList("a", "b", "c");
            final FmParam param = new FmParam("iterableParam", list);
            Assertions.assertThat(param.name()).isEqualTo("iterableParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with LocalDateTime value")
        void shouldCreateWithLocalDateTimeValue() {
            final LocalDateTime now = LocalDateTime.now();
            final FmParam param = new FmParam("dateTimeParam", now);
            Assertions.assertThat(param.name()).isEqualTo("dateTimeParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }
    }

    @Nested
    @DisplayName("Date constructor tests")
    final class DateConstructorTests {

        @Test
        @DisplayName("Should create FmParam with LocalDate value")
        void shouldCreateWithLocalDateValue() {
            final LocalDate today = LocalDate.now();
            final FmParam param = new FmParam("localDateParam", today);
            Assertions.assertThat(param.name()).isEqualTo("localDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(LdVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with java.util.Date value")
        void shouldCreateWithUtilDateValue() {
            final java.util.Date date = new java.util.Date();
            final FmParam param = new FmParam("utilDateParam", date);
            Assertions.assertThat(param.name()).isEqualTo("utilDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(JdVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with java.sql.Date value")
        void shouldCreateWithSqlDateValue() {
            final java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            final FmParam param = new FmParam("sqlDateParam", date);
            Assertions.assertThat(param.name()).isEqualTo("sqlDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(JdsqlVal.class);
        }
    }

    @Nested
    @DisplayName("Method tests")
    final class MethodTests {

        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {
            final FmParam param = new FmParam(ParamTest.TEST_NAME, "testValue");
            Assertions.assertThat(param.name()).isEqualTo(ParamTest.TEST_NAME);
        }

        @Test
        @DisplayName("Should return correct value")
        void shouldReturnCorrectValue() {
            final Value expected = new StrVal("testValue");
            final FmParam param = new FmParam(ParamTest.TEST_NAME, expected);
            Assertions.assertThat(param.value()).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should create renamed copy with new name")
        void shouldCreateRenamedCopy() {
            final Value expected = new StrVal("testValue");
            final FmParam original = new FmParam("originalName", expected);
            final Param renamed = original.rename("newName");
            Assertions.assertThat(renamed.name()).isEqualTo("newName");
            Assertions.assertThat(renamed.value()).isEqualTo(expected);
            Assertions.assertThat(original.name()).isEqualTo("originalName");
        }

        @Test
        @DisplayName("Should throw exception when renaming with null name")
        void shouldThrowExceptionWhenRenamingWithNullName() {
            final FmParam param = new FmParam(ParamTest.TEST_NAME, "testValue");
            Assertions.assertThatThrownBy(() -> param.rename(null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("Equals and HashCode tests")
    final class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal when name and value are the same")
        void shouldBeEqualWhenNameAndValueAreSame() {
            final FmParam first = new FmParam(ParamTest.TEST, ParamTest.VALUE);
            final FmParam second = new FmParam(ParamTest.TEST, ParamTest.VALUE);
            Assertions.assertThat(first).isEqualTo(second);
            Assertions.assertThat(first.hashCode()).isEqualTo(second.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when names differ")
        void shouldNotBeEqualWhenNamesDiffer() {
            final FmParam first = new FmParam("test1", ParamTest.VALUE);
            final FmParam second = new FmParam("test2", ParamTest.VALUE);
            Assertions.assertThat(first).isNotEqualTo(second);
        }

        @Test
        @DisplayName("Should not be equal when values differ")
        void shouldNotBeEqualWhenValuesDiffer() {
            final FmParam first = new FmParam(ParamTest.TEST, "value1");
            final FmParam second = new FmParam(ParamTest.TEST, "value2");
            Assertions.assertThat(first).isNotEqualTo(second);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            final FmParam param = new FmParam(ParamTest.TEST, ParamTest.VALUE);
            Assertions.assertThat(param).isNotEqualTo(null);
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            final FmParam param = new FmParam(ParamTest.TEST, ParamTest.VALUE);
            final String str = ParamTest.TEST;
            Assertions.assertThat(param).isNotEqualTo(str);
        }
    }

    @Nested
    @DisplayName("ToString tests")
    final class ToStringTests {

        @Test
        @DisplayName("Should return non-empty string representation")
        void shouldReturnNonEmptyStringRepresentation() {
            final FmParam param = new FmParam(ParamTest.TEST, ParamTest.VALUE);
            Assertions.assertThat(param.toString())
                .isNotEmpty()
                .contains("FmParam");
        }
    }

    @Nested
    @DisplayName("Edge cases")
    final class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values in wrapper constructors")
        void shouldHandleNullValuesInWrapperConstructors() {
            Assertions.assertThatThrownBy(
                () -> {
                    final Param param = new FmParam(ParamTest.TEST, (String) null);
                    param.value().val();
                }).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThatThrownBy(
                () -> {
                    final Param param = new FmParam(ParamTest.TEST, (Long) null);
                    param.value().val();
                }).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThatThrownBy(
                () -> {
                    final Param param = new FmParam(ParamTest.TEST, (Integer) null);
                    param.value().val();
                }).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThatThrownBy(
                () -> {
                    final Param param = new FmParam(ParamTest.TEST, (Double) null);
                    param.value().val();
                }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should handle special characters in name")
        void shouldHandleSpecialCharactersInName() {
            final String specials = "test@#$%^&*()_+-={}[]|\\:;\"'<>?,./`~";
            final FmParam param = new FmParam(specials, ParamTest.VALUE);
            Assertions.assertThat(param.name()).isEqualTo(specials);
        }

        @Test
        @DisplayName("Should handle Unicode characters in name")
        void shouldHandleUnicodeCharactersInName() {
            final String unicode = "тест_परीक्षण_测试_テスト";
            final FmParam param = new FmParam(unicode, ParamTest.VALUE);
            Assertions.assertThat(param.name()).isEqualTo(unicode);
        }
    }
}
