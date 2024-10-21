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

import com.google.common.collect.ImmutableList;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;

/**
 * All freemarker template's parameters.
 * Need for base freemarker functionality inside fmrk4sql, when we are able to combine
 * different terms or fully sql bodies in single template file
 *
 * @since 0.1.0
 */
@EqualsAndHashCode
public final class FmParams implements Params {
    /**
     * The empty parameters for query.
     */
    public static final FmParams EMPTY = new FmParams(Collections.EMPTY_LIST);

    /**
     * Wrapper over java object for freemarker parser.
     * Need to initialize one time and multiple use in get method.
     */
    private static final ObjectWrapper WRAPPER = new DefaultObjectWrapper(
        freemarker.template.Configuration.VERSION_2_3_32
    );

    /**
     * Store params for freemarker template parse.
     */
    private final Iterable<Param> params;

    public FmParams() {
        this(new ArrayList<>(0));
    }

    public FmParams(final Param... params) {
        this(Arrays.asList(params));
    }

    public FmParams(final Iterable<Param> params) {
        this.params = params;
    }

    @Override
    public TemplateModel get(final String name) throws TemplateModelException {
        return FmParams.WRAPPER.wrap(this.param(name).value().val());
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
        return this.params.iterator().hasNext();
    }

    @Override
    public Params with(final Param param) {
        final Collection<Param> items = new ArrayList<>(this.list().size());
        for (final Param item : this.params) {
            items.add(item);
        }
        items.add(param);
        return new FmParams(items);
    }

    @Override
    public List<Param> list() {
        return ImmutableList.copyOf(this.params);
    }
}
