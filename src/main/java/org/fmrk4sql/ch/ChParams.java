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

package org.fmrk4sql.ch;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.fmrk4sql.Bindable;
import org.fmrk4sql.FmParam;
import org.fmrk4sql.Param;
import org.fmrk4sql.ParamConverter;
import org.fmrk4sql.Params;
import org.fmrk4sql.params.IntParam;
import org.fmrk4sql.params.JdParam;
import org.fmrk4sql.params.JsqlParam;
import org.fmrk4sql.params.LdParam;
import org.fmrk4sql.params.LdtParam;
import org.fmrk4sql.params.StrParam;

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
     * Convertors map for params to bind in clickhouse queries.
     */
    private static final Map<Class<?>, ParamConverter> CONVERTERS =
        new MapOf(
            new MapEntry<>(FmParam.class, new FmConverter()),
            new MapEntry<>(StrParam.class, new StrConverter()),
            new MapEntry<>(LdParam.class, new LdConverter()),
            new MapEntry<>(LdtParam.class, new LdtConverter()),
            new MapEntry<>(JdParam.class, new JdConverter()),
            new MapEntry<>(JsqlParam.class, new JsqlConverter()),
            new MapEntry<>(IntParam.class, new IntConverter())
        );

    /**
     * Link at decorated object.
     */
    private final transient Params params;

    /**
     * Map of param convertors.
     */
    private Map<Class<?>, ParamConverter> cvts;

    public ChParams(final Params params) {
        this(params, ChParams.CONVERTERS);
    }

    public ChParams(final Params params, final Map<Class<?>, ParamConverter> converters) {
        this.params = params;
        this.cvts = converters;
    }

    @Override
    public Bindable with(final Class<?> clazz, final ParamConverter converter) {
        final Map<Class<?>, ParamConverter> items = new MapOf<>(this.cvts);
        items.put(clazz, converter);
        return new ChParams(this.params, items);
    }

    @Override
    public Map<String, Object> map() {
        final Map<String, Object> result = new HashMap<>();
        for (final Param param : this.params.list()) {
            result.put(param.name(), this.cvts.get(param.getClass()).convert(param));
        }
        return result;
    }
}
