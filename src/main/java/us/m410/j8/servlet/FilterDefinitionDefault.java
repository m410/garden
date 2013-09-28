package us.m410.j8.servlet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class FilterDefinitionDefault implements FilterDefinition {
    private String name;
    private String className;
    private String params;
    private String mapping;

    public FilterDefinitionDefault(String name, String className, String params, String mapping) {
        this.name = name;
        this.className = className;
        this.params = params;
        this.mapping = mapping;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getParams() {
        return params;
    }


    public String getMapping() {
        return mapping;
    }
}
