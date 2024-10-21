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
import java.util.Collections;
import java.util.List;

/**
 * Null params for cases when freemarker template without params.
 *
 * @since 0.1.0
 */
public final class NullParams implements Params {
    @Override
    public TemplateModel get(final String key) throws TemplateModelException {
        final ObjectWrapper wrapper = new DefaultObjectWrapper(
            freemarker.template.Configuration.VERSION_2_3_32
        );
        return wrapper.wrap(null);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Params with(final Param param) {
        throw new UnsupportedOperationException("There are no way to add param in NullParams");
    }

    @Override
    public List<Param> list() {
        return Collections.emptyList();
    }

    @Override
    public Param param(final String name) {
        return null;
    }

}
