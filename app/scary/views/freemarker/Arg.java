package scary.views.freemarker;

public class Arg {
    final String name;
    final Object value;

    public Arg(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static Arg arg(String name, Object value) {
        return new Arg(name, value);
    }
}
