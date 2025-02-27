package com.example.News.Service;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;

@Service
public class TemplateService {
    private final VelocityEngine velocityEngine;

    public TemplateService() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.path", "src/main/resources/templates");
        velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
    }

    public String generateHtml(String templateName, Object data, Locale locale) {
        Template template = velocityEngine.getTemplate(templateName);
        VelocityContext context = new VelocityContext();
        context.put("newsList", data);  // Veriyi template'e ekle
        context.put("dateTool", new DateTool());  // DateTool nesnesini ekle
        context.put("locale", locale);  // Locale'i ekle

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}
