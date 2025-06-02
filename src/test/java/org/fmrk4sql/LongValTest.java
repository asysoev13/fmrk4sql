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
import org.fmrk4sql.val.LongVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for LongVal class.
 * @since 0.1.0
 */
final class LongValTest {

    @Test
    void shouldReturnSameLongValue() {
        final LongVal longval = new LongVal(123_456_789L);
        Assertions
            .assertThat(longval.val())
            .isEqualTo(123_456_789L);
    }

    @Test
    void shouldConvertToSameLongValue() {
        final LongVal longval = new LongVal(987_654_321L);
        Assertions
            .assertThat(longval.convert())
            .isEqualTo(987_654_321L);
    }

    @Test
    void shouldHandleZeroValue() {
        final LongVal longval = new LongVal(0L);
        Assertions
            .assertThat(longval.val())
            .isEqualTo(0L);
        Assertions
            .assertThat(longval.convert())
            .isEqualTo(0L);
    }

    @Test
    void shouldHandleNegativeValue() {
        final LongVal longval = new LongVal(-999_999_999L);
        Assertions
            .assertThat(longval.val())
            .isEqualTo(-999_999_999L);
        Assertions
            .assertThat(longval.convert())
            .isEqualTo(-999_999_999L);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final LongVal longval = new LongVal(null);
        Assertions
            .assertThatThrownBy(longval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final LongVal longval = new LongVal(null);
        Assertions
            .assertThatThrownBy(longval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
