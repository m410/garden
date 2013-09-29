package us.m410.j8.servlet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ServletDefinition {
    private String name;
    private String className;
    private String params;
    private String urlPattern;

    public ServletDefinition(String name, String className, String params, String urlPattern) {
        this.name = name;
        this.className = className;
        this.params = params;
        this.urlPattern = urlPattern;
    }

    public String getParams() {
        return params;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }
}
