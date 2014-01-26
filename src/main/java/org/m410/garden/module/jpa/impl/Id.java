package org.m410.garden.module.jpa.impl;

/**
 * To be able to use the Dao interface, it needs a defined ID in the persistent
 * entities.
 *
 * @author Michael Fortin
 */
public interface Id<T> {
    T getId();
}
