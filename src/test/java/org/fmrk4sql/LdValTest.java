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
import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.LdVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for LdVal class.
 * @since 0.1.0
 */
final class LdValTest {

    @Test
    void shouldReturnSameLocalDateValue() {
        final LocalDate localdate = LocalDate.of(2024, 1, 1);
        final LdVal ldval = new LdVal(localdate);
        Assertions
            .assertThat(ldval.val())
            .isSameAs(localdate);
    }

    @Test
    void shouldConvertToSameLocalDateValue() {
        final LocalDate localdate = LocalDate.of(2023, 12, 31);
        final LdVal ldval = new LdVal(localdate);
        Assertions
            .assertThat(ldval.convert())
            .isSameAs(localdate);
    }

    @Test
    void shouldHandleCurrentDate() {
        final LocalDate today = LocalDate.now();
        final LdVal ldval = new LdVal(today);
        Assertions
            .assertThat(ldval.val())
            .isEqualTo(today);
        Assertions
            .assertThat(ldval.convert())
            .isEqualTo(today);
    }

    @Test
    void shouldHandleSpecificDate() {
        final LocalDate specificdate = LocalDate.of(2000, 1, 1);
        final LdVal ldval = new LdVal(specificdate);
        Assertions
            .assertThat(ldval.val())
            .isEqualTo(specificdate);
        Assertions
            .assertThat(ldval.convert())
            .isEqualTo(specificdate);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final LdVal ldval = new LdVal(null);
        Assertions
            .assertThatThrownBy(ldval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final LdVal ldval = new LdVal(null);
        Assertions
            .assertThatThrownBy(ldval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
