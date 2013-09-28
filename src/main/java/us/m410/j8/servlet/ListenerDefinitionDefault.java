package us.m410.j8.servlet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ListenerDefinitionDefault implements ListenerDefinition {

    private String name;
    private String className;

    public ListenerDefinitionDefault(String name, String className) {
        this.name = name;
        this.className = className;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
