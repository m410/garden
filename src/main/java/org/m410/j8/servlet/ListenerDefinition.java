package org.m410.j8.servlet;

/**
 * Create a listener class and adds it the the servlet container at runtime.
 *
 * @author Michael Fortin
 */
public final class ListenerDefinition {

    private String name;
    private String className;

    public ListenerDefinition(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
