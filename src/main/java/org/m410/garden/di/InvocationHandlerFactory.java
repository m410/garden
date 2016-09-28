package org.m410.garden.di;

/**
 * @author Michael Fortin
 */
public interface InvocationHandlerFactory {
    // todo need to merge this with ThreadLocalSessionFactory
    <T> T proxy(Class<T> interfce, T instance);
}
