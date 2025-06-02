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
import org.fmrk4sql.val.ObjVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for ObjVal class.
 * @since 0.1.0
 */
final class ObjValTest {

    @Test
    void shouldReturnSameObjectValue() {
        final Object obj = new Object();
        final ObjVal objval = new ObjVal(obj);
        Assertions
            .assertThat(objval.val())
            .isSameAs(obj);
    }

    @Test
    void shouldConvertToSameObjectValue() {
        final String stringobj = "test string";
        final ObjVal objval = new ObjVal(stringobj);
        Assertions
            .assertThat(objval.convert())
            .isSameAs(stringobj);
    }

    @Test
    void shouldHandleIntegerObject() {
        final Integer intobj = 42;
        final ObjVal objval = new ObjVal(intobj);
        Assertions
            .assertThat(objval.val())
            .isEqualTo(intobj);
        Assertions
            .assertThat(objval.convert())
            .isEqualTo(intobj);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final ObjVal objval = new ObjVal(null);
        Assertions
            .assertThatThrownBy(objval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final ObjVal objval = new ObjVal(null);
        Assertions
            .assertThatThrownBy(objval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
