package com.github.iceant.spring.jdbc.dao.generator.detail.util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.StringWriter;

public class MustacheTemplateUtil {
    public static String render(String templateName, Object models){
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(templateName);
        StringWriter stringWriter = new StringWriter();
        mustache.execute(stringWriter, models);
        return stringWriter.toString();
    }
}
