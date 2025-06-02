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

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateModelException;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.fmrk4sql.val.LdVal;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.Test;

/**
 * Tests for CaseParams class.
 * @since 0.1.0
 */
final class CaseParamsTest {
    /**
     * Date for tests.
     */
    private final LocalDate date = LocalDate.of(2024, 01, 01);

    @Test
    void fromLowerCamelTest() throws TemplateModelException {
        final Params params = new CaseParams(
            new FmParams(
                new FmParam("fooBar", new StrVal("value"))
            ),
            CaseFormat.LOWER_CAMEL,
            CaseFormat.LOWER_UNDERSCORE
        );
        Assertions.assertThat(params.get("foo_bar").toString()).isEqualTo("value");
    }

    @Test
    void caseParamsToList() {
        final Params params = new CaseParams(
            new FmParams(
                new FmParam("paramDate", new LdVal(this.date)),
                new FmParam("tableName", "fmrk_table")
            ),
            CaseFormat.LOWER_CAMEL,
            CaseFormat.LOWER_UNDERSCORE
        );
        final List<Param> expected = new ListOf(
            new FmParam("param_date", this.date),
            new FmParam("table_name", "fmrk_table")
        );
        Assertions.assertThat(params.list()).isEqualTo(expected);
    }
}

