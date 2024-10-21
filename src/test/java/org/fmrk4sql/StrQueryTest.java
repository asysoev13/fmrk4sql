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
import org.assertj.core.api.Assertions;
import org.fmrk4sql.fake.FakeOrder;
import org.fmrk4sql.fake.FakePageable;
import org.fmrk4sql.val.BoolVal;
import org.fmrk4sql.val.StrVal;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Tests for StrQuery class functionality.
 * @since 0.1.0
 */
final class StrQueryTest {

    @Test
    void parseSimpleQueryNoParam() throws TemplateException, IOException {
        final Query query = new StrQuery("select count()");
        Assertions.assertThat(query.parse(FmParams.EMPTY))
            .isEqualTo("select count()");
    }

    @Test
    void parseSimpleQueryIfBoolean() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "<#-- @vtlvariable name=\"plan\" type=\"java.lang.Boolean\" -->",
            "<#if plan==true>",
            "select sum(plan_value) from table",
            "<#else>",
            "select sum(fact_value) from table",
            "</#if>"
        );
        final Params params = new FmParams(new FmParam("plan", new BoolVal(true)));
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(plan_value) from table");
    }

    @Test
    void parseSimpleQueryTableName() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "select sum(plan_value) from ${table_name}"
        );
        final Params params = new FmParams(new FmParam("table_name", new StrVal("fmrk_table")));
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(plan_value) from fmrk_table");
    }

    @Test
    void parsePageableParams() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "select sum(plan_value) from ${table_name1} ",
            "limit ${size} offset ${page}"
        );
        final Params fmparams = new FmParams(
            new FmParam("table_name1", new StrVal("fmrk_table"))
        );
        final Params params = new PageParams(
            fmparams,
            new FakePageable(0L, 10, Collections.EMPTY_LIST)
        );
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(plan_value) from fmrk_table limit 10 offset 0");
    }

    @Test
    void parseOnlyPageableParams() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "select sum(plan_value) from constant_table ",
            "limit ${size} offset ${page}"
        );
        final Params params = new PageParams(
            new NullParams(),
            new FakePageable(0L, 10, Collections.EMPTY_LIST)
        );
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo("select sum(plan_value) from constant_table limit 10 offset 0");
    }

    @Test
    void parsePageableOrderable() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "select col1, col2 from ${table_name2}",
            "<#if orders?has_content> ",
            "order by ",
            "<#list orders as ord>",
            "${ord.col()} ${ord.direction()} ",
            "</#list>",
            "</#if>",
            "limit ${size} offset ${page}"
        );
        final Params fmparams = new FmParams(
            new FmParam("table_name2", new StrVal("orderable_table"))
        );
        final Params params = new PageParams(
            fmparams,
            new FakePageable(0L, 10, new FakeOrder("col1", "ASC"))
        );
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo(
                "select col1, col2 from orderable_table order by col1 ASC limit 10 offset 0"
            );
    }

    @Test
    void parsePageableNullOrderableNull() throws TemplateException, IOException {
        final String template = String.join(
            "",
            "select col1, col2 from ${table_name2} ",
            "<#if orders?has_content> ",
            "order by ",
            "<#list orders as ord>",
            "${ord.col()} ${ord.direction()} ",
            "</#list>",
            "</#if>",
            "limit ${size}  offset ${page}"
        );
        final Params fmparams = new FmParams(
            new FmParam("table_name2", new StrVal("orderable_table"))
        );
        final Params params = new PageParams(
            fmparams,
            new FakePageable(0L, 10, Orderable.NO_ORDER)
        );
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo(
                "select col1, col2 from orderable_table limit 10  offset 0"
            );
    }

    @Test
    void parseSpringPageable() throws TemplateException, IOException {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.by(new Sort.Order(Sort.Direction.ASC, "test_col"))
        );
        final String template =  String.join(
            "",
            "select col1, col2 from ${table_name3}",
            "<#if orders?has_content> ",
            "order by ",
            "<#list orders as ord>",
            "${ord.col()} ${ord.direction()} ",
            "</#list>",
            "</#if>",
            "limit ${size} offset ${page}"
        );
        final Params fmparams = new FmParams(
            new FmParam("table_name3", new StrVal("orderable_table"))
        );
        final Params params = new PageParams(fmparams, new SpringPage(spring));
        final Query query = new StrQuery(template);
        Assertions.assertThat(query.parse(params))
            .isEqualTo(
                "select col1, col2 from orderable_table order by test_col ASC limit 20 offset 0"
            );
    }
}
