package us.m410.j8.servlet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ServletDefinitionDefault implements ServletDefinition {
    private String name;
    private String className;
    private String params;
    private String urlPattern;

    public ServletDefinitionDefault(String name, String className, String params, String urlPattern) {
        this.name = name;
        this.className = className;
        this.params = params;
        this.urlPattern = urlPattern;
    }

    @Override
    public String getParams() {
        return params;
    }

    @Override
    public String getUrlPattern() {
        return urlPattern;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getName() {
        return name;
    }
}
