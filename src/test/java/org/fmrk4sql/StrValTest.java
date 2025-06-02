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

import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for StrVal class.
 * @since 0.1.0
 */
final class StrValTest {

    @Test
    void shouldReturnSameStringValue() {
        final StrVal strval = new StrVal("test");
        Assertions
            .assertThat(strval.val())
            .isEqualTo("test");
    }

    @Test
    void shouldConvertToSameStringValue() {
        final StrVal strval = new StrVal("hello world");
        Assertions
            .assertThat(strval.convert())
            .isEqualTo("hello world");
    }

    @Test
    void shouldHandleEmptyString() {
        final StrVal strval = new StrVal("");
        Assertions
            .assertThat(strval.val())
            .isEqualTo("");
        Assertions
            .assertThat(strval.convert())
            .isEqualTo("");
    }

    @Test
    void shouldHandleSpecialCharacters() {
        final StrVal strval = new StrVal("@#$%^&*()");
        Assertions
            .assertThat(strval.val())
            .isEqualTo("@#$%^&*()");
        Assertions
            .assertThat(strval.convert())
            .isEqualTo("@#$%^&*()");
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final StrVal strval = new StrVal(null);
        Assertions
            .assertThatThrownBy(strval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final StrVal strval = new StrVal(null);
        Assertions
            .assertThatThrownBy(strval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
