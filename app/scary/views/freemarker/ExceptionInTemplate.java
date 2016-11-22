package scary.views.freemarker;

import play.Play;

import java.io.InputStream;

public class ExceptionInTemplate extends play.api.PlayException.ExceptionSource {

    final String template;
    final Integer line;
    final Integer position;

    public ExceptionInTemplate(String template, Integer line, Integer position, String description, Throwable cause) {
        super("Freemarker error", description, cause);
        this.template = template;
        this.line = line;
        this.position = position;
    }

    public Integer line() {
        return line;
    }

    public Integer position() {
        return position;
    }

    public String input() {
        InputStream is = Play.application().resourceAsStream("/views/" + template);
        if(is != null) {
            try {
                StringBuilder c = new StringBuilder();
                byte[] b = new byte[1024];
                int read = -1;
                while((read = is.read(b)) > 0) {
                    c.append(new String(b, 0, read));
                }
                return c.toString();
            } catch(Throwable e) {
                //
            }
        }
        return "(source missing";
    }

    public String sourceName() {
        return template;
    }

}
