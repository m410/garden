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

    private String[] mappings;

    public ServletDefinition(String name, String className, String... mappings) {
        this.name = name;
        this.className = className;
        this.mappings = mappings;
    }

    public String getParams() {
        return params;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String[] mappings() {
        return mappings;
    }
}
