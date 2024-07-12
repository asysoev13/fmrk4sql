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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

/**
 * Tests for Pageable interface with Spring implementation.
 * @since 0.1.0
 */
final class PageableTest {

    @Test
    void getPage() {
        final org.springframework.data.domain.Pageable spring =
            org.springframework.data.domain.Pageable.ofSize(200);
        final Pageable pageable = new SpringPage(spring);
        Assertions.assertEquals(0, pageable.page());
    }

    @Test
    void getSize() {
        final org.springframework.data.domain.Pageable spring =
            org.springframework.data.domain.Pageable.ofSize(200);
        final Pageable pageable = new SpringPage(spring);
        Assertions.assertEquals(200, pageable.size());
    }

    @Test
    void exceptionNoPaged() {
        final org.springframework.data.domain.Pageable spring =
            org.springframework.data.domain.Pageable.unpaged();
        final Pageable pageable = new SpringPage(spring);
        final IllegalArgumentException expected = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> pageable.size(),
            "Expected exception, where pageable is not defined by Spring"
        );
        Assertions.assertTrue(
            expected.getMessage().contains("Pageable is not defined by Spring in pageable argument")
        );
    }

    @Test
    void ascOrder() {
        final Orderable orderable = new SpringOrder(
            new Sort.Order(Sort.Direction.ASC, "test_col")
        );
        Assertions.assertEquals("ASC", orderable.direction());
    }

    @Test
    void colOrder() {
        final Orderable orderable = new SpringOrder(
            new Sort.Order(Sort.Direction.ASC, "test_col")
        );
        Assertions.assertEquals("test_col", orderable.col());
    }
}
