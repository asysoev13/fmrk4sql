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

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Freemarker template parameter.
 * Contains name and value of parameter
 * @param <P> - Type of parameter
 * @since 0.1.0
 */
@EqualsAndHashCode
@ToString
public final class FmParam<P> implements Param {
    /**
     * Parameter name.
     */
    private final String pname;

    /**
     * Parameter value.
     */
    private final P pval;

    public FmParam(final String name, final P val) {
        this.pname = name;
        this.pval = val;
    }

    @Override
    public String name() {
        return this.pname;
    }

    @Override
    public P value() {
        return this.pval;
    }

    @Override
    public Param copy(final String name) {
        final Param result;
        if (name == null || "".equals(name)) {
            result = new FmParam(this.pname, this.pval);
        } else {
            result = new FmParam(name, this.pval);
        }
        return result;
    }
}
