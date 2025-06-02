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

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.LdtVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for LdtVal class.
 * @since 0.1.0
 */
final class LdtValTest {

    @Test
    void shouldReturnSameLocalDateTimeValue() {
        final LocalDateTime expected = LocalDateTime.of(2024, 1, 1, 10, 30, 0);
        final LdtVal ldtval = new LdtVal(expected);
        Assertions
            .assertThat(ldtval.val())
            .isSameAs(expected);
    }

    @Test
    void shouldConvertToSameLocalDateTimeValue() {
        final LocalDateTime expected = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        final LdtVal ldtval = new LdtVal(expected);
        Assertions
            .assertThat(ldtval.convert())
            .isSameAs(expected);
    }

    @Test
    void shouldHandleCurrentDateTime() {
        final LocalDateTime now = LocalDateTime.now();
        final LdtVal ldtval = new LdtVal(now);
        Assertions
            .assertThat(ldtval.val())
            .isEqualTo(now);
        Assertions
            .assertThat(ldtval.convert())
            .isEqualTo(now);
    }

    @Test
    void shouldHandleSpecificDateTime() {
        final LocalDateTime expected = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
        final LdtVal ldtval = new LdtVal(expected);
        Assertions
            .assertThat(ldtval.val())
            .isEqualTo(expected);
        Assertions
            .assertThat(ldtval.convert())
            .isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final LdtVal ldtval = new LdtVal(null);
        Assertions
            .assertThatThrownBy(ldtval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final LdtVal ldtval = new LdtVal(null);
        Assertions
            .assertThatThrownBy(ldtval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
