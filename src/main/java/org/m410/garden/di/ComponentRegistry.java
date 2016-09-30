package org.m410.garden.di;

/**
 * @author Michael Fortin
 */
public interface ComponentRegistry {
    <T> T typeOf(Class<T> myServiceClass);

    <T> T typeOf(Class<T> myServiceClass, String name);

    <T> T[] arrayOf(Class<T> myServiceClass);
}
