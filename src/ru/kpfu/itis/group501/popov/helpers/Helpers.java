package ru.kpfu.itis.group501.popov.helpers;

import ru.kpfu.itis.group501.popov.singletons.ConfigSingleton;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Helpers {
    public static void render(HttpServletRequest request, HttpServletResponse response, String tmpl, Map<String, Object> root) throws IOException {
        Configuration cnfg = ConfigSingleton.getConfig(request.getServletContext());
        Template template = cnfg.getTemplate(tmpl);
        try {
            template.process(root, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void render(HttpServletRequest request, HttpServletResponse response, String tmpl) throws IOException {
        Configuration cnfg = ConfigSingleton.getConfig(request.getServletContext());
        Template template = cnfg.getTemplate(tmpl);
        try {
            template.process(null, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}