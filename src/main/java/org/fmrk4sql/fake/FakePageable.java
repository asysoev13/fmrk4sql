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

package org.fmrk4sql.fake;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import org.fmrk4sql.Orderable;
import org.fmrk4sql.Pageable;

/**
 * Fake pageable for checking pageable queries.
 *
 * @since 0.1.0
 */
public final class FakePageable implements Pageable {

    /**
     * Page number.
     */
    private final Long fpage;

    /**
     * Elements on page.
     */
    private final Integer fsize;

    /**
     * Orders for query.
     */
    private final Iterable<Orderable> forder;

    public FakePageable(final Long fpage, final Integer fsize, final Orderable... forder) {
        this(fpage, fsize, Arrays.asList(forder));
    }

    public FakePageable(final Long fpage, final Integer fsize, final Iterable<Orderable> forder) {
        this.fpage = fpage;
        this.fsize = fsize;
        this.forder = forder;
    }

    @Override
    public Long page() {
        return this.fpage;
    }

    @Override
    public Integer size() {
        return this.fsize;
    }

    @Override
    public List<Orderable> orders() {
        return ImmutableList.copyOf(this.forder);
    }
}
