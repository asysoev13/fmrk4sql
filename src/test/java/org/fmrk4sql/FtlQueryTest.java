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

import freemarker.template.TemplateException;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for FtlQuery class.
 * @since 0.1.0
 */
final class FtlQueryTest {
    /**
     * Params factory.
     */
    private final ParamsFactory factory = new FmParamsFactory();

    @Test
    void parseSimpleQueryNoParam() throws TemplateException, IOException {
        final Query query = new FtlQuery("/ftltest", "simple_query_no_param.sql");
        Assertions.assertThat(query.parse(FmParams.EMPTY)).isEqualTo("select count()");
    }

    @Test
    void parseSimpleQueryIfBoolean() throws TemplateException, IOException {
        final Params params = this.factory.params("plan", false);
        final Query query = new FtlQuery(
            "/ftltest", "parse_simple_query_if_boolean.sql"
        );
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(fact_value) from table");
    }

    @Test
    void parseSimpleQueryTableName() throws TemplateException, IOException {
        final Params params = this.factory.params("table_name", "fmrk_table");
        final Query query = new FtlQuery(
            "/ftltest", "parse_simple_query_table_name.sql"
        );
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(plan_value) from fmrk_table");
    }
}
