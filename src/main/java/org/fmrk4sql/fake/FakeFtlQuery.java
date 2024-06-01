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

package org.fmrk4sql.fake;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringWriter;
import java.util.TimeZone;
import org.fmrk4sql.Params;
import org.fmrk4sql.Query;

/**
 * Query that parse templates in classpath (resources dir).
 * In parse method uses ClassTemplateLoader for load templates from classpath, so when use
 * this class be sure that you want to work with templates from classpath
 *
 * This fake for set class and allow use test resources dir
 *
 * @since 0.1.0
 */
public final class FakeFtlQuery implements Query {
    /**
     * Path for freemarker templates store.
     */
    private final String path;

    /**
     * Name of freemarker template.
     */
    private final String name;

    public FakeFtlQuery(final String path, final String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public String parse(final Params params) throws IOException, TemplateException {
        final Template template = this.config().getTemplate(this.name);
        final StringWriter result = new StringWriter(1024);
        template.process(params, result);
        return result.toString();
    }

    private Configuration config() throws IOException {
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), this.path));
        return cfg;
    }
}
