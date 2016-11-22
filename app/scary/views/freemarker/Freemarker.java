package scary.views.freemarker;

import freemarker.ext.beans.BeansWrapper;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import play.Play;
import play.twirl.api.Html;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Freemarker {
    // Freemarker configuration

    static {
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_SLF4J);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    static {
        cfg.setClassForTemplateLoading(Freemarker.class, "/scary/views/");
        if(Play.isDev()) {
            cfg.setTemplateUpdateDelayMilliseconds(0);
        }
    }

    // Main API

    public static Html view(String template, Arg... args) {
        Map<String, Object> root = new HashMap<>();
        for(Arg arg: args) {
            root.put(arg.name, arg.value);
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Writer out = new StringWriter();
            Thread.currentThread().setContextClassLoader(Play.application().classloader());
            root.put("Router", new BeansWrapper(Configuration.VERSION_2_3_23).getStaticModels().get("controllers.routes"));
            cfg.getTemplate(template).process(root, out);
            out.flush();
            return Html.apply(out.toString());
        } catch(FileNotFoundException e) {
            throw new TemplateNotFoundException(template, Thread.currentThread().getStackTrace());
        } catch(freemarker.core.ParseException e) {
            throw new ExceptionInTemplate(template, e.getLineNumber(), e.getColumnNumber(), e.getMessage(), e);
        } catch(IOException ex) {
            if(ex.getCause() instanceof freemarker.core.ParseException) {
                freemarker.core.ParseException e = (freemarker.core.ParseException)ex.getCause();
                throw new ExceptionInTemplate(template, e.getLineNumber(), e.getColumnNumber(), e.getMessage(), e);
            }
            throw new RuntimeException(ex);
        } catch(TemplateException ex) {
            String ftStack = ex.getFTLInstructionStack().replace('\n', ' ');
            Integer line = Integer.parseInt(grep("line ([0-9]+)", ftStack));
            Integer position = Integer.parseInt(grep("column ([0-9]+)", ftStack));
            String tmpl = grep("in ([^ \\]]+)", ftStack);
            throw new ExceptionInTemplate(tmpl, line, position, ex.getMessage(), ex);
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    // Utils

    private static String grep(String regex, String str) {
        Matcher m = Pattern.compile(regex).matcher(str);
        m.find();
        return m.group(1);
    }
}
