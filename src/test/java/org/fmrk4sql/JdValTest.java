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

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.JdVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for JdVal class.
 * @since 0.1.0
 */
final class JdValTest {

    @Test
    void shouldReturnSameDateValue() {
        final Date date = new Date();
        final JdVal jdval = new JdVal(date);
        Assertions
            .assertThat(jdval.val())
            .isSameAs(date);
    }

    @Test
    void shouldConvertToSameDateValue() {
        final Date date = new Date(1_640_995_200_000L);
        final JdVal jdval = new JdVal(date);
        Assertions
            .assertThat(jdval.convert())
            .isSameAs(date);
    }

    @Test
    void shouldHandleSpecificDateValue() {
        final Date specificdate = new Date(1_640_995_200_000L);
        final JdVal jdval = new JdVal(specificdate);
        Assertions
            .assertThat(jdval.val())
            .isEqualTo(specificdate);
        Assertions
            .assertThat(jdval.convert())
            .isEqualTo(specificdate);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final JdVal jdval = new JdVal(null);
        Assertions
            .assertThatThrownBy(jdval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final JdVal jdval = new JdVal(null);
        Assertions
            .assertThatThrownBy(jdval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
