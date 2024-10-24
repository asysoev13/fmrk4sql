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

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.fmrk4sql.val.IntVal;
import org.fmrk4sql.val.LongVal;
import org.fmrk4sql.val.ObjVal;

/**
 * Decorator at Params with pageable functionality.
 * The most frequent case of use fmrk4sql is queries with pageable support.
 * This decorator allow support
 *
 * @since 0.1.0
 */
public final class PageParams implements Params {
    /**
     * Store params for freemarker template parse.
     */
    private final List<Param> params;

    /**
     * Link to decorated object.
     */
    private final transient Params origin;

    /**
     * Link to decorated object.
     */
    private final transient Pageable pageable;

    /**
     * Wrapper over java object for freemarker parser.
     * Need to initialize one time and multiple use in get method.
     */
    private final ObjectWrapper wrapper;

    public PageParams(final Params origin, final Pageable pageable) {
        this.origin = origin;
        this.pageable = pageable;
        this.params = new ArrayList<>(this.origin.list().size());
        this.wrapper = new DefaultObjectWrapper(freemarker.template.Configuration.VERSION_2_3_32);
    }

    @Override
    public TemplateModel get(final String name) throws TemplateModelException {
        if (this.params.isEmpty()) {
            this.params.addAll(this.origin.list());
            this.params.add(new FmParam("page", new LongVal(this.pageable.page())));
            this.params.add(new FmParam("size", new IntVal(this.pageable.size())));
            this.params.add(new FmParam("orders", new ObjVal(this.pageable.orders())));
        }
        final Param param = this.param(name);
        TemplateModel result = null;
        if (param != null) {
            result = this.wrapper.wrap(this.param(name).value().val());
        }
        return result;
    }

    @Override
    public Param param(final String name) {
        Param result = null;
        for (final Param param : this.params) {
            if (name.equals(param.name())) {
                result = param;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.params.isEmpty();
    }

    @Override
    public Params with(final Param param) {
        return new PageParams(this.origin.with(param), this.pageable);
    }

    @Override
    public List<Param> list() {
        if (this.params.isEmpty()) {
            this.params.addAll(this.origin.list());
            this.params.add(new FmParam("page", new LongVal(this.pageable.page())));
            this.params.add(new FmParam("size", new IntVal(this.pageable.size())));
            this.params.add(new FmParam("orders", new ObjVal(this.pageable.orders())));
        }
        return Collections.unmodifiableList(this.params);
    }
}
