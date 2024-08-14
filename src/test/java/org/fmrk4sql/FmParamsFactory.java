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

/**
 * Factory for creating FmParams.
 * @since 0.1.0
 * @checkstyle ParameterNameCheck (63 lines)
 */
@SuppressWarnings("PMD.UseObjectForClearerAPI")
final class FmParamsFactory implements ParamsFactory {
    @Override
    public Params params(final String key, final Object val) {
        return new FmParams(List.of(new FmParam(key, val)));
    }

    @Override
    public Params params(
        final String key1, final Object val1,
        final String key2, final Object val2
    ) {
        return new FmParams(List.of(new FmParam(key1, val1), new FmParam(key2, val2)));
    }

    @Override
    public Params params(
        final String key1, final Object val1,
        final String key2, final Object val2,
        final String key3, final Object val3
    ) {
        return new FmParams(
            List.of(
                new FmParam(key1, val1),
                new FmParam(key2, val2),
                new FmParam(key3, val3)
            ));
    }

    @Override
    public Params params(
        final String key1, final Object val1,
        final String key2, final Object val2,
        final String key3, final Object val3,
        final String key4, final Object val4
    ) {
        return new FmParams(
            List.of(
                new FmParam(key1, val1),
                new FmParam(key2, val2),
                new FmParam(key3, val3),
                new FmParam(key4, val4)
            ));
    }
}
