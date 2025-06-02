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

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.IterableVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for IterableVal class.
 * @since 0.1.0
 */
final class IterableValTest {

    @Test
    void shouldReturnSameIterableValue() {
        final List<String> list = Arrays.asList("first", "second", "third");
        final IterableVal iterval = new IterableVal(list);
        Assertions
            .assertThat(iterval.val())
            .isSameAs(list);
    }

    @Test
    void shouldConvertToSameIterableValue() {
        final List<Integer> numbers = Arrays.asList(1, 2, 3);
        final IterableVal iterval = new IterableVal(numbers);
        Assertions
            .assertThat(iterval.convert())
            .isSameAs(numbers);
    }

    @Test
    void shouldHandleEmptyIterable() {
        final List<String> empty = Arrays.asList();
        final IterableVal iterval = new IterableVal(empty);
        Assertions
            .assertThat(iterval.val())
            .isSameAs(empty);
        Assertions
            .assertThat(iterval.convert())
            .isSameAs(empty);
    }

    @Test
    void shouldThrowExceptionWhenValIsCalledOnNullValue() {
        final IterableVal iterval = new IterableVal(null);
        Assertions
            .assertThatThrownBy(iterval::val)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConvertIsCalledOnNullValue() {
        final IterableVal iterval = new IterableVal(null);
        Assertions
            .assertThatThrownBy(iterval::convert)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }
}
