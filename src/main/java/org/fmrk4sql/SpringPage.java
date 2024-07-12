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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pagination that based on Spring pagination implementation.
 *
 * @since 0.1.0
 */
public final class SpringPage implements Pageable {

    /**
     * Pageable of spring framework.
     */
    private final org.springframework.data.domain.Pageable pageable;

    public SpringPage(final org.springframework.data.domain.Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public Long page() {
        final Long result;
        if (this.pageable.isPaged()) {
            result = this.pageable.getOffset();
        } else {
            throw new IllegalArgumentException(
                "Pageable is not defined by Spring in pageable argument"
            );
        }
        return result;
    }

    @Override
    public Integer size() {
        final Integer result;
        if (this.pageable.isPaged()) {
            result = this.pageable.getPageSize();
        } else {
            throw new IllegalArgumentException(
                "Pageable is not defined by Spring in pageable argument"
            );
        }
        return result;
    }

    @Override
    public List<Orderable> orders() {
        final List<Orderable> result;
        if (this.pageable.getSort().isSorted()) {
            result = this.pageable.getSort().get()
                .map(p -> new SpringOrder(p)).collect(Collectors.toList());
        } else {
            result = Collections.EMPTY_LIST;
        }
        return result;
    }
}
