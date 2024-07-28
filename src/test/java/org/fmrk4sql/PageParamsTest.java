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
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Tests for PageParams class.
 * @since 0.1.0
 */
final class PageParamsTest {
    @Test
    void pageParamsToList() {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.by(List.of(new Sort.Order(Sort.Direction.ASC, "test_col")))
        );
        final Params actual = new PageParams(
            new FmParams(
                List.of(new FmParam("table_name4", "orderable_table"))
            ),
            new SpringPage(spring)
        );
        final List<Param> expected = List.of(
            new FmParam("table_name4", "orderable_table"),
            new FmParam("page", 0L),
            new FmParam("size", 20),
            new FmParam(
                "orders",
                List.of(new SpringOrder(new Sort.Order(Sort.Direction.ASC, "test_col")))
            )
        );
        org.assertj.core.api.Assertions.assertThat(actual.toList())
            .hasSize(expected.size())
            .extracting("pname", "pval")
            .containsExactlyElementsOf(
                expected.stream().map(
                    p -> org.assertj.core.api.Assertions.tuple(p.name(), p.value())
                ).collect(Collectors.toList())
            );
    }

    @Test
    void pageParamsToMap() {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.by(List.of(new Sort.Order(Sort.Direction.ASC, "test_col")))
        );
        final Params actual = new PageParams(
            new FmParams(
                List.of(new FmParam("table_name5", "orderable_table"))
            ),
            new SpringPage(spring)
        );
        final Map<String, Object> expected = Map.of(
            "table_name5",
            "orderable_table",
            "page",
            0L,
            "size",
            20,
            "orders",
            List.of(new SpringOrder(new Sort.Order(Sort.Direction.ASC, "test_col")))
        );
        org.assertj.core.api.Assertions.assertThat(actual.map()).isEqualTo(expected);
    }
}
