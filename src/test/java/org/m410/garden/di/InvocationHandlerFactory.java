package org.m410.garden.di;

/**
 * @author Michael Fortin
 */
public interface InvocationHandlerFactory {
    <T> T proxy(Class<T> interfce, Object instance);
}
