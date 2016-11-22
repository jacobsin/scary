package scary.views.freemarker;

public class TemplateNotFoundException extends RuntimeException {

    private final StackTraceElement[] callerStack;

    public TemplateNotFoundException(String template, StackTraceElement[] stack) {
        super("Template " + template + " is missing.");
        callerStack = new StackTraceElement[stack.length - 2];
        System.arraycopy(stack, 2, callerStack, 0, callerStack.length);
    }

    public StackTraceElement[] getStackTrace() {
        return callerStack;
    }

}
