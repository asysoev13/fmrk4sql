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

import org.assertj.core.api.Assertions;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for param class.
 * @since 0.1.0
 */
final class ParamTest {
    @Test
    void fmParamRenameWithNull() {
        final Param param = new FmParam("table_name", new StrVal("fmrk_table"));
        final NullPointerException exception = Assertions.catchThrowableOfType(
            () -> param.rename(null), NullPointerException.class
        );
        Assertions
            .assertThat(exception)
            .isInstanceOf(NullPointerException.class)
            .hasMessage("name is marked non-null but is null");
    }

    @Test
    void fmParamRename() {
        final Param param = new FmParam("table_name", new StrVal("fmrk_table"));
        final Param expected = new FmParam("new_name", new StrVal("fmrk_table"));
        Assertions
            .assertThat(param.rename("new_name"))
            .isEqualTo(expected);
    }
}
