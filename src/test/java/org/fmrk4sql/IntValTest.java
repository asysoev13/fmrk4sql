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
import org.fmrk4sql.val.IntVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for IntVal class.
 * @since 0.1.0
 */
final class IntValTest {

    @Test
    void shouldReturnSameIntegerValue() {
        final IntVal intval = new IntVal(42);
        Assertions
            .assertThat(intval.val())
            .isEqualTo(42);
    }

    @Test
    void shouldConvertToSameIntegerValue() {
        final IntVal intval = new IntVal(123);
        Assertions
            .assertThat(intval.convert())
            .isEqualTo(123);
    }

    @Test
    void shouldHandleZeroValue() {
        final IntVal intval = new IntVal(0);
        Assertions
            .assertThat(intval.val())
            .isEqualTo(0);
        Assertions
            .assertThat(intval.convert())
            .isEqualTo(0);
    }

    @Test
    void shouldHandleNegativeValue() {
        final IntVal intval = new IntVal(-999);
        Assertions
            .assertThat(intval.val())
            .isEqualTo(-999);
        Assertions
            .assertThat(intval.convert())
            .isEqualTo(-999);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final IntVal intval = new IntVal(null);
        Assertions
            .assertThatThrownBy(intval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final IntVal intval = new IntVal(null);
        Assertions
            .assertThatThrownBy(intval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
