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
    class ConstructorValidationTests {

        @Test
        @DisplayName("Should throw exception when name is null")
        void shouldThrowExceptionWhenNameIsNull() {
            Assertions.assertThatThrownBy(() -> new FmParam(null, new StrVal("test")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter name cannot be null or empty");
        }

        @Test
        @DisplayName("Should throw exception when name is empty string")
        void shouldThrowExceptionWhenNameIsEmpty() {
            Assertions.assertThatThrownBy(() -> new FmParam("", new StrVal("test")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter name cannot be null or empty");
        }

        @Test
        @DisplayName("Should throw exception when name contains only whitespace")
        void shouldThrowExceptionWhenNameIsWhitespace() {
            Assertions.assertThatThrownBy(() -> new FmParam("   ", new StrVal("test")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter name cannot be null or empty");
        }

        @Test
        @DisplayName("Should throw exception when value is null")
        void shouldThrowExceptionWhenValueIsNull() {
            Assertions.assertThatThrownBy(() -> new FmParam("test", (Value) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter value cannot be null");
        }

        @Test
        @DisplayName("Should create valid object with proper name and value")
        void shouldCreateValidObject() {
            Value testValue = new StrVal("test");
            FmParam param = new FmParam("testName", testValue);

            Assertions.assertThat(param.name()).isEqualTo("testName");
            Assertions.assertThat(param.value()).isEqualTo(testValue);
        }
    }

    @Nested
    @DisplayName("String constructor tests")
    class StringConstructorTests {

        @Test
        @DisplayName("Should create FmParam with String value")
        void shouldCreateWithStringValue() {
            FmParam param = new FmParam("stringParam", "testString");

            Assertions.assertThat(param.name()).isEqualTo("stringParam");
            Assertions.assertThat(param.value()).isInstanceOf(StrVal.class);
        }

        @Test
        @DisplayName("Should throw exception when String constructor has null name")
        void shouldThrowExceptionWithNullNameInStringConstructor() {
            Assertions.assertThatThrownBy(() -> new FmParam(null, "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter name cannot be null or empty");
        }
    }

    @Nested
    @DisplayName("Numeric constructor tests")
    class NumericConstructorTests {

        @Test
        @DisplayName("Should create FmParam with Long value")
        void shouldCreateWithLongValue() {
            FmParam param = new FmParam("longParam", 123L);

            Assertions.assertThat(param.name()).isEqualTo("longParam");
            Assertions.assertThat(param.value()).isInstanceOf(LongVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Integer value")
        void shouldCreateWithIntegerValue() {
            FmParam param = new FmParam("intParam", 456);

            Assertions.assertThat(param.name()).isEqualTo("intParam");
            Assertions.assertThat(param.value()).isInstanceOf(IntVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Double value")
        void shouldCreateWithDoubleValue() {
            FmParam param = new FmParam("doubleParam", 123.45);

            Assertions.assertThat(param.name()).isEqualTo("doubleParam");
            Assertions.assertThat(param.value()).isInstanceOf(DblVal.class);
        }
    }

    @Nested
    @DisplayName("Object constructor tests")
    class ObjectConstructorTests {

        @Test
        @DisplayName("Should create FmParam with Object value")
        void shouldCreateWithObjectValue() {
            Object testObj = new Object();
            FmParam param = new FmParam("objParam", testObj);

            Assertions.assertThat(param.name()).isEqualTo("objParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with Iterable value")
        void shouldCreateWithIterableValue() {
            List<String> testList = Arrays.asList("a", "b", "c");
            FmParam param = new FmParam("iterableParam", testList);

            Assertions.assertThat(param.name()).isEqualTo("iterableParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with LocalDateTime value")
        void shouldCreateWithLocalDateTimeValue() {
            LocalDateTime now = LocalDateTime.now();
            FmParam param = new FmParam("dateTimeParam", now);

            Assertions.assertThat(param.name()).isEqualTo("dateTimeParam");
            Assertions.assertThat(param.value()).isInstanceOf(ObjVal.class);
        }
    }

    @Nested
    @DisplayName("Date constructor tests")
    class DateConstructorTests {

        @Test
        @DisplayName("Should create FmParam with LocalDate value")
        void shouldCreateWithLocalDateValue() {
            LocalDate today = LocalDate.now();
            FmParam param = new FmParam("localDateParam", today);

            Assertions.assertThat(param.name()).isEqualTo("localDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(LdVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with java.util.Date value")
        void shouldCreateWithUtilDateValue() {
            java.util.Date date = new java.util.Date();
            FmParam param = new FmParam("utilDateParam", date);

            Assertions.assertThat(param.name()).isEqualTo("utilDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(JdVal.class);
        }

        @Test
        @DisplayName("Should create FmParam with java.sql.Date value")
        void shouldCreateWithSqlDateValue() {
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            FmParam param = new FmParam("sqlDateParam", sqlDate);

            Assertions.assertThat(param.name()).isEqualTo("sqlDateParam");
            Assertions.assertThat(param.value()).isInstanceOf(JdsqlVal.class);
        }
    }

    @Nested
    @DisplayName("Method tests")
    class MethodTests {

        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {
            FmParam param = new FmParam("testName", "testValue");

            Assertions.assertThat(param.name()).isEqualTo("testName");
        }

        @Test
        @DisplayName("Should return correct value")
        void shouldReturnCorrectValue() {
            Value testValue = new StrVal("testValue");
            FmParam param = new FmParam("testName", testValue);

            Assertions.assertThat(param.value()).isEqualTo(testValue);
        }

        @Test
        @DisplayName("Should create renamed copy with new name")
        void shouldCreateRenamedCopy() {
            Value originalValue = new StrVal("testValue");
            FmParam original = new FmParam("originalName", originalValue);

            Param renamed = original.rename("newName");

            Assertions.assertThat(renamed.name()).isEqualTo("newName");
            Assertions.assertThat(renamed.value()).isEqualTo(originalValue);
            Assertions.assertThat(original.name()).isEqualTo("originalName"); // Original unchanged
        }

        @Test
        @DisplayName("Should throw exception when renaming with null name")
        void shouldThrowExceptionWhenRenamingWithNullName() {
            FmParam param = new FmParam("testName", "testValue");

            Assertions.assertThatThrownBy(() -> param.rename(null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("Equals and HashCode tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal when name and value are the same")
        void shouldBeEqualWhenNameAndValueAreSame() {
            FmParam param1 = new FmParam("test", "value");
            FmParam param2 = new FmParam("test", "value");

            Assertions.assertThat(param1).isEqualTo(param2);
            Assertions.assertThat(param1.hashCode()).isEqualTo(param2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when names differ")
        void shouldNotBeEqualWhenNamesDiffer() {
            FmParam param1 = new FmParam("test1", "value");
            FmParam param2 = new FmParam("test2", "value");

            Assertions.assertThat(param1).isNotEqualTo(param2);
        }

        @Test
        @DisplayName("Should not be equal when values differ")
        void shouldNotBeEqualWhenValuesDiffer() {
            FmParam param1 = new FmParam("test", "value1");
            FmParam param2 = new FmParam("test", "value2");

            Assertions.assertThat(param1).isNotEqualTo(param2);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            FmParam param = new FmParam("test", "value");

            Assertions.assertThat(param).isNotEqualTo(null);
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            FmParam param = new FmParam("test", "value");
            String str = "test";

            Assertions.assertThat(param).isNotEqualTo(str);
        }
    }

    @Nested
    @DisplayName("ToString tests")
    class ToStringTests {

        @Test
        @DisplayName("Should return non-empty string representation")
        void shouldReturnNonEmptyStringRepresentation() {
            FmParam param = new FmParam("test", "value");

            Assertions.assertThat(param.toString())
                .isNotEmpty()
                .contains("FmParam");
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null values in wrapper constructors")
        void shouldHandleNullValuesInWrapperConstructors() {
            Assertions.assertThatThrownBy(() -> new FmParam("test", (String) null))
                .isInstanceOf(IllegalArgumentException.class);

            Assertions.assertThatThrownBy(() -> new FmParam("test", (Long) null))
                .isInstanceOf(IllegalArgumentException.class);

            Assertions.assertThatThrownBy(() -> new FmParam("test", (Integer) null))
                .isInstanceOf(IllegalArgumentException.class);

            Assertions.assertThatThrownBy(() -> new FmParam("test", (Double) null))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should handle special characters in name")
        void shouldHandleSpecialCharactersInName() {
            String specialName = "test@#$%^&*()_+-={}[]|\\:;\"'<>?,./`~";
            FmParam param = new FmParam(specialName, "value");

            Assertions.assertThat(param.name()).isEqualTo(specialName);
        }

        @Test
        @DisplayName("Should handle Unicode characters in name")
        void shouldHandleUnicodeCharactersInName() {
            String unicodeName = "тест_परीक्षण_测试_テスト";
            FmParam param = new FmParam(unicodeName, "value");

            Assertions.assertThat(param.name()).isEqualTo(unicodeName);
        }
    }
}
