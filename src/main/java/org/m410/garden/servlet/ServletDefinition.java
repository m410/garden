package org.m410.garden.servlet;


import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Servlet definition embedded into the application class the get initialized and
 * added to the servlet container at runtime.
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

    public void configure(ServletContext servletContext) {
        ServletRegistration.Dynamic d = servletContext.addServlet(getName(), getClassName());
        d.addMapping(mappings());
    }

    public void configure(ServletContext servletContext, Servlet s) {
        ((ProxyServlet)s).setDelegateName(getClassName());
        ServletRegistration.Dynamic d = servletContext.addServlet(getName(), s);
        d.addMapping(mappings());
    }
}
