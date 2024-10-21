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

import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.fmrk4sql.val.IntVal;
import org.fmrk4sql.val.LongVal;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Tests for PageParams class.
 * @since 0.1.0
 */
final class PageParamsTest {
    /**
     * Params factory.
     */
    @Test
    void pageParamsToList() {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.by(new Sort.Order(Sort.Direction.ASC, "test_col"))
        );
        final Params params = new FmParams(
            new FmParam("table_name4", new StrVal("orderable_table"))
        );
        final Params actual = new PageParams(params, new SpringPage(spring));
        final List<Param> expected = new ListOf(
            new FmParam("table_name4", "orderable_table"),
            new FmParam("page", new LongVal(0L)),
            new FmParam("size", new IntVal(20)),
            new FmParam(
                "orders",
                new ListOf(new SpringOrder(new Sort.Order(Sort.Direction.ASC, "test_col")))
            )
        );
        Assertions.assertThat(actual.list())
            .hasSize(expected.size())
            .extracting(Param::name, Param::value)
            .containsExactlyElementsOf(
                expected.stream().map(
                    p -> Assertions.tuple(p.name(), p.value())
                ).collect(Collectors.toList())
            );
    }

    @Test
    void pageParamsUnordered() {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.unsorted()
        );
        final Params params = new FmParams(
            new FmParam("table_name5", new StrVal("noorderable_table"))
        );
        final Params actual = new PageParams(params, new SpringPage(spring));
        final List<Param> expected = new ListOf(
            new FmParam("table_name5", "noorderable_table"),
            new FmParam("page", new LongVal(0L)),
            new FmParam("size", new IntVal(20)),
            new FmParam(
                "orders",
                Orderable.NO_ORDER
            )
        );
        Assertions.assertThat(actual.list())
            .hasSize(expected.size())
            .extracting(Param::name, Param::value)
            .containsExactlyElementsOf(
                expected.stream().map(
                    p -> Assertions.tuple(p.name(), p.value())
                ).collect(Collectors.toList())
            );
    }

    @Test
    void pageParamsUnpaged() {
        final Params params = new FmParams(
            new FmParam("table_name6", new StrVal("unpaged_table"))
        );
        final Params actual = new PageParams(params, new SpringPage(Pageable.unpaged()));
        final IllegalArgumentException exception = Assertions.catchThrowableOfType(
            () -> actual.list(), IllegalArgumentException.class
        );
        Assertions
            .assertThat(exception)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Pageable is not defined by Spring in pageable argument");
    }
}
