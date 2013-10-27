package org.m410.j8.servlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class FilterDefinition {
    private String name;
    private String className;
    private String[] urlPatterns;
    private boolean matchAfter = true;
    private EnumSet<DispatcherType> dispatchTypes = EnumSet.of(DispatcherType.REQUEST);

    public FilterDefinition(String name, String className, String... urlPatterns) {
        this.name = name;
        this.className = className;
        this.urlPatterns = urlPatterns;
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
}
