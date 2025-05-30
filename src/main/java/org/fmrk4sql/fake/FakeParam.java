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

import org.fmrk4sql.FmParam;
import org.fmrk4sql.Param;
import org.fmrk4sql.Value;

/**
 * Fake typed params.
 *
 * @since 0.1.0
 */
public final class FakeParam implements Param {

    /**
     * Param name.
     */
    private final String pname;

    /**
     * Param type.
     */
    private final Value pval;

    public FakeParam(final String pname, final Value pval) {
        this.pname = pname;
        this.pval = pval;
    }

    @Override
    public String name() {
        return this.pname;
    }

    @Override
    public Value value() {
        return this.pval;
    }

    @Override
    public Param rename(final String name) {
        final Param result;
        if ("".equals(name)) {
            result = new FmParam(this.pname, this.pval);
        } else {
            result = new FmParam(name, this.pval);
        }
        return result;
    }

    @Override
    public String toString() {
        return this.value().toString();
    }
}
