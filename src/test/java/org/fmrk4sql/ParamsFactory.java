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

/**
 * Factory for create params, use only parameter name and parameter value.
 * @since 0.1.0
 * @checkstyle ParameterNameCheck (72 lines)
 * @checkstyle ParameterNumberCheck (54 lines)
 */
@SuppressWarnings("PMD.UseObjectForClearerAPI")
public interface ParamsFactory {

    /**
     * Create one param.
     * @param key Param key
     * @param val Param value
     * @return Created params.
     */
    Params params(String key, Object val);

    /**
     * Create two param.
     * @param key1 Param key
     * @param val1 Param value
     * @param key2 Param key
     * @param val2 Param value
     * @return Created params.
     */
    Params params(
        String key1, Object val1,
        String key2, Object val2
    );

    /**
     * Create three param.
     * @param key1 Param key
     * @param val1 Param value
     * @param key2 Param key
     * @param val2 Param value
     * @param key3 Param key
     * @param val3 Param value
     * @return Created params.
     */
    Params params(
        String key1, Object val1,
        String key2, Object val2,
        String key3, Object val3
    );

    /**
     * Create four param.
     * @param key1 Param key
     * @param val1 Param value
     * @param key2 Param key
     * @param val2 Param value
     * @param key3 Param key
     * @param val3 Param value
     * @param key4 Param key
     * @param val4 Param value
     * @return Created params.
     */
    Params params(
        String key1, Object val1,
        String key2, Object val2,
        String key3, Object val3,
        String key4, Object val4
    );
}
