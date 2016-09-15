package org.m410.garden.di;


import java.lang.reflect.InvocationHandler;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentFactory<T> {
    T make(InvocationHandlerFactory txmgr, Object... arguments);
}
