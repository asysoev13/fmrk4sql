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

package org.fmrk4sql.val;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.fmrk4sql.Value;

/**
 * Fmrk4sql LocalDate value for queries.
 * @since 0.1.0
 */
@EqualsAndHashCode
@ToString
public final class LdVal implements Value<LocalDate, LocalDate> {

    /**
     * Stored value of fmrk4sql wrapper.
     */
    private final LocalDate value;

    public LdVal(final LocalDate value) {
        this.value = value;
    }

    @Override
    public LocalDate val() {
        return this.value;
    }

    @Override
    public LocalDate convert() {
        return this.value;
    }
}
