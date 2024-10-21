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
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.util.ArrayList;
import java.util.List;

/**
 * Decorator at Params with parameter names case formatting.
 * For example HTTP query with parameter api/get?fooBar=13 and in database
 * column name is foo_bar.
 * In that case CaseParams class can be used with
 * input = CaseFormat.LOWER_CAMEL
 * output = CaseFormat.LOWER_UNDERSCORE
 *
 * @since 0.1.0
 */
public final class CaseParams implements Params {

    /**
     * Link to decorated object.
     */
    private final transient Params origin;

    /**
     * Wrapper over java object for freemarker parser.
     * Need to initialize one time and multiple use in get method.
     */
    private final ObjectWrapper wrapper;

    /**
     * Base parameter format.
     * Format name of parameter names should be converted from
     */
    private final CaseFormat input;

    /**
     * Target parameter format.
     * Format name of parameter names should be converted to
     */
    private final CaseFormat output;

    public CaseParams(final Params origin, final CaseFormat input, final CaseFormat output) {
        this.origin = origin;
        this.wrapper = new DefaultObjectWrapper(freemarker.template.Configuration.VERSION_2_3_32);
        this.input = input;
        this.output = output;
    }

    @Override
    public TemplateModel get(final String name) throws TemplateModelException {
        return this.wrapper.wrap(this.param(name).value().val());
    }

    @Override
    public Param param(final String name) {
        Param result = null;
        for (final Param param : this.origin.list()) {
            if (name.equals(this.input.to(this.output, param.name()))) {
                result = param;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.origin.list().isEmpty();
    }

    @Override
    public Params with(final Param param) {
        return new CaseParams(this.origin.with(param), this.input, this.output);
    }

    @Override
    public List<Param> list() {
        final List<Param> result = new ArrayList<>(this.origin.list().size());
        for (final Param param : this.origin.list()) {
            result.add(param.rename(this.input.to(this.output, param.name())));
        }
        return result;
    }
}
