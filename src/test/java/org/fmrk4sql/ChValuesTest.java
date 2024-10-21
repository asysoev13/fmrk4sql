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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.fmrk4sql.ch.ChBool;
import org.fmrk4sql.ch.ChDbl;
import org.fmrk4sql.ch.ChInt;
import org.fmrk4sql.ch.ChJd;
import org.fmrk4sql.ch.ChJdsql;
import org.fmrk4sql.ch.ChLd;
import org.fmrk4sql.ch.ChLdt;
import org.fmrk4sql.ch.ChList;
import org.fmrk4sql.ch.ChListStr;
import org.fmrk4sql.ch.ChLong;
import org.fmrk4sql.ch.ChStr;
import org.junit.jupiter.api.Test;

/**
 * Tests for Clickhouse specific queries.
 * @since 0.1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ChValuesTest {
    @Test
    void chBoolTest() {
        final Value val = new ChBool(true);
        Assertions
            .assertThat(val.convert())
            .isEqualTo(true);
    }

    @Test
    void chDblTest() {
        final Value val = new ChDbl(3.14);
        Assertions
            .assertThat(val.convert())
            .isEqualTo(3.14);
    }

    @Test
    void chLongTest() {
        final Value val = new ChLong(99L);
        Assertions
            .assertThat(val.convert())
            .isEqualTo(99L);
    }

    @Test
    void chIntTest() {
        final Value val = new ChInt(3);
        Assertions
            .assertThat(val.convert())
            .isEqualTo(3);
    }

    @Test
    void chJdTest() throws ParseException {
        final java.util.Date judate = new SimpleDateFormat(
            "yyyy-MM-dd'T'hh:mm:ss",
            Locale.getDefault()
        ).parse("2024-01-01T10:11:12");
        final Value val = new ChJd(judate);
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'2024-01-01T10:11:12'");
    }

    @Test
    void chJdsqlTest() throws ParseException {
        final java.util.Date judate = new SimpleDateFormat(
            "yyyy-MM-dd'T'hh:mm:ss",
            Locale.getDefault()
        ).parse("2024-01-01T10:11:13");
        final Value val = new ChJdsql(new java.sql.Date(judate.getTime()));
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'2024-01-01T10:11:13'");
    }

    @Test
    void chLdTest() {
        final Value val = new ChLd(LocalDate.of(2024, 1, 1));
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'2024-01-01'");
    }

    @Test
    void chLdtTest() {
        final Value val = new ChLdt(LocalDateTime.of(2024, 1, 1, 10, 11, 12));
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'2024-01-01T10:11:12'");
    }

    @Test
    void chListTest() {
        final Value val = new ChList(
            new ListOf(
                new ChStr("first"),
                new ChStr("second"),
                new ChStr("third")
            )
        );
        Assertions
            .assertThat(val.convert())
            .isEqualTo(new ListOf<>("'first'", "'second'", "'third'"));
    }

    @Test
    void chListStrTest() {
        final Value val = new ChListStr(
            new ListOf(
                new ChStr("fourth"),
                new ChStr("fifth"),
                new ChStr("six")
            )
        );
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'fourth','fifth','six'");
    }

    @Test
    void chStrTest() {
        final Value val = new ChStr("Str1");
        Assertions
            .assertThat(val.convert())
            .isEqualTo("'Str1'");
    }
}
