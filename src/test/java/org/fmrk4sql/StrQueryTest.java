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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for StrQuery class functionality.
 * @since 0.1.0
 */
final class StrQueryTest {

    @Test
    void parseSimpleQueryNoParam() throws TemplateException, IOException {
        final Params params = new FmParams(Collections.EMPTY_LIST);
        final Query query = new StrQuery("<#ftl encoding=\"utf-8\">\nselect count()");
        Assertions.assertEquals("select count()", query.parse(params));
    }

    @Test
    void parseSimpleQueryIfBoolean() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "<#ftl encoding=\"utf-8\">\n",
            "<#-- @vtlvariable name=\"plan\" type=\"java.lang.Boolean\" -->",
            "<#if plan==true>",
            "select sum(plan_value) from table",
            "<#else>",
            "select sum(fact_value) from table",
            "</#if>"
        );
        final Params params = new FmParams(List.of(new FmParam("plan", true)));
        final Query query = new StrQuery(template);
        Assertions.assertEquals("select sum(plan_value) from table", query.parse(params));
    }

    @Test
    void parseSimpleQueryTableName() throws TemplateException, IOException {
        final String template =  String.join(
            "",
            "<#ftl encoding=\"utf-8\">\n",
            "select sum(plan_value) from ${table_name}"
        );
        final Params params = new FmParams(
            List.of(new FmParam("table_name", "fmrk_table"))
        );
        final Query query = new StrQuery(template);
        Assertions
            .assertEquals("select sum(plan_value) from fmrk_table", query.parse(params));
    }
}
