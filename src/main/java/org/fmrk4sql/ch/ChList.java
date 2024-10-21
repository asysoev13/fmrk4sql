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

package org.fmrk4sql.ch;

import java.util.ArrayList;
import java.util.List;
import org.fmrk4sql.Value;
import org.fmrk4sql.val.IterableVal;

/**
 * Clickhouse List value for queries.
 * @since 0.1.0
 */
public final class ChList implements Value {

    /**
     * Link to decorated object.
     */
    private final Value<Iterable<Value>, Iterable> origin;

    public ChList(final List val) {
        this(new IterableVal(val));
    }

    private ChList(final Value origin) {
        this.origin = origin;
    }

    @Override
    public Iterable<Value> val() {
        return this.origin.val();
    }

    @Override
    public Iterable convert() {
        final List result = new ArrayList<>(10);
        for (final Value val : this.origin.val()) {
            result.add(val.convert());
        }
        return result;
    }
}
