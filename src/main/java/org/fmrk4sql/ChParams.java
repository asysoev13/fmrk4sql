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

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;

/**
 * Decorator formats values to Clickhouse format for variables binding.
 * In query:
 * select foo from bar where id=:id_var
 * :id_var - variable for binding
 * Finally if :id_var is String, map return: id_var:'String'
 * @since 0.1.0
 */
@EqualsAndHashCode
public final class ChParams implements Bindable {

    /**
     * Link at decorated object.
     */
    private final transient Params params;

    public ChParams(final Params params) {
        this.params = params;
    }

    @Override
    public Map<String, Object> map() {
        final Map<String, Object> result = new HashMap<>();
        for (final Param param : this.params.list()) {
            result.put(param.name(), param.value());
        }
        return result;
    }
}
