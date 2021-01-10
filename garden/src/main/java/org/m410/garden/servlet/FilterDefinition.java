package org.m410.garden.servlet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * Used by the application to add filters to the application at runtime.
 *
 * @author Michael Fortin
 */
public final class FilterDefinition {
    private String name;
    private String className;
    private String[] urlPatterns;
    private boolean matchAfter = true;
    private EnumSet<DispatcherType> dispatchTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

    public FilterDefinition(String name, String className, String... urlPatterns) {
        this.name = name;
        this.className = className;
        this.urlPatterns = Arrays.asList(urlPatterns).stream()
                .filter(s->s!=null && !"".equals(s))
                .collect(Collectors.toList())
                .toArray(new String[1]);
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public boolean matchAfter() {
        return matchAfter;
    }

    public EnumSet<DispatcherType> dispatchTypes() {
        return dispatchTypes;
    }

    public String[] urlPatterns() {
        return urlPatterns;
    }

    public void configure(ServletContext servletContext) {
        FilterRegistration.Dynamic d = servletContext.addFilter(getName(), getClassName());
        d.addMappingForUrlPatterns(dispatchTypes(), matchAfter(), urlPatterns());
    }

    public void configure(ServletContext servletContext, Filter f) {
        try {
            f.getClass().getMethod("setDelegateName",String.class).invoke(f,getClassName());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        FilterRegistration.Dynamic d = servletContext.addFilter(getName(), f);

        if(d!=null)
            d.addMappingForUrlPatterns(dispatchTypes(), matchAfter(), urlPatterns());
    }
}
