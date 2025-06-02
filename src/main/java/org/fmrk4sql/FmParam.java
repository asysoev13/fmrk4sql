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
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.fmrk4sql.val.DblVal;
import org.fmrk4sql.val.IntVal;
import org.fmrk4sql.val.JdVal;
import org.fmrk4sql.val.JdsqlVal;
import org.fmrk4sql.val.LdVal;
import org.fmrk4sql.val.LongVal;
import org.fmrk4sql.val.ObjVal;
import org.fmrk4sql.val.StrVal;

/**
 * Freemarker template parameter.
 * Contains name and value of parameter
 * @since 0.1.0
 */
@EqualsAndHashCode
@ToString
public class FmParam implements Param {
    /**
     * Parameter name.
     */
    private final String pname;

    /**
     * Parameter value.
     */
    private final Value pval;

    public FmParam(final String name, final Value val) {
        this.pname = name;
        this.pval = val;
    }

    public FmParam(final String name, final String val) {
        this(name, new StrVal(val));
    }

    public FmParam(final String name, final Long val) {
        this(name, new LongVal(val));
    }

    public FmParam(final String name, final Integer val) {
        this(name, new IntVal(val));
    }

    public FmParam(final String name, final Double val) {
        this(name, new DblVal(val));
    }

    public FmParam(final String name, final Object val) {
        this(name, new ObjVal(val));
    }

    public FmParam(final String name, final Iterable val) {
        this(name, new ObjVal(val));
    }

    public FmParam(final String name, final LocalDateTime val) {
        this(name, new ObjVal(val));
    }

    public FmParam(final String name, final LocalDate val) {
        this(name, new LdVal(val));
    }

    public FmParam(final String name, final java.util.Date val) {
        this(name, new JdVal(val));
    }

    public FmParam(final String name, final java.sql.Date val) {
        this(name, new JdsqlVal(val));
    }

    /**
     * Freemarker template parameter name.
     * @return Parameter name
     */
    public String name() {
        if (this.pname == null || this.pname.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return this.pname;
    }

    /**
     * Freemarker template parameter value.
     * @return Parameter value
     */
    public Value value() {
        if (this.pval == null) {
            throw new IllegalArgumentException("Parameter value cannot be null");
        }
        return this.pval;
    }

    /**
     * Return copy of object with changed name.
     * @param name New name of new parameter
     * @return Parameter copy
     */
    public Param rename(@NonNull final String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return new FmParam(name, this.pval);
    }
}
