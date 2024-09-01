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

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.fmrk4sql.ch.ChParams;
import org.fmrk4sql.params.IntParam;
import org.fmrk4sql.params.LdParam;
import org.fmrk4sql.params.LdtParam;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Tests for Clickhouse decorator of params.
 * @since 0.1.0
 */
final class ChParamsTest {

    /**
     * Params factory.
     */
    private final ParamsFactory factory = new FmParamsFactory();

    /**
     * Date for tests.
     */
    private final LocalDate date = LocalDate.of(2024, 1, 1);

    /**
     * Date time for tests.
     */
    private final LocalDateTime datetime = LocalDateTime.of(2024, 1, 1, 10, 11, 12);

    @Test
    void convertDateParamsToMap() {
        final Params params = new FmParams(
            new LdParam("date", this.date),
            new LdtParam("datetime", this.datetime)
        );
        final Bindable chparams = new ChParams(params);
        Assertions
            .assertThat(chparams.map())
            .contains(
                Assertions.entry("date", "'2024-01-01'"),
                Assertions.entry("datetime", "'2024-01-01T10:11:12'")
            );
    }

    @Test
    void convertIntParamsToMap() {
        final Params params = new FmParams(
            new IntParam("intvalue1", 13),
            new FmParam("fmvalue1", "value"),
            new FmParam("strvalue1", "'value'")
        );
        final Bindable chparams = new ChParams(params);
        Assertions
            .assertThat(chparams.map())
            .contains(
                Assertions.entry("intvalue1", 13),
                Assertions.entry("fmvalue1", "value"),
                Assertions.entry("strvalue1", "'value'")
            );
    }

    @Test
    void caseParamsToMap() {
        final Params params = this.factory.params(
            "paramDate",
            this.date,
            "tableName",
            "fmrk_table"
        );
        final Bindable chparams = new ChParams(params);
        Assertions.assertThat(chparams.map())
            .contains(
                Assertions.entry("paramDate", this.date),
                Assertions.entry("tableName", "fmrk_table")
            );
    }

    @Test
    void pageParamsToMap() {
        final Pageable spring = PageRequest.of(
            0,
            20,
            Sort.by(new Sort.Order(Sort.Direction.ASC, "test_col"))
        );
        final Params params = this.factory.params("table_name5", "orderable_table");
        final Params pageparams = new PageParams(params, new SpringPage(spring));
        final Bindable chparams = new ChParams(pageparams);
        Assertions.assertThat(chparams.map())
            .contains(
                Assertions.entry("table_name5", "orderable_table"),
                Assertions.entry("page", 0L),
                Assertions.entry("size", 20),
                Assertions.entry(
                    "orders",
                    new ListOf(
                        new SpringOrder(new Sort.Order(Sort.Direction.ASC, "test_col"))
                    )
                )
            );
    }

}
