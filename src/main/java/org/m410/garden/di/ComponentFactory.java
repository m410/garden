package org.m410.garden.di;


import org.m410.garden.zone.ZoneHandlerFactory;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentFactory<T> {
    T make(ZoneHandlerFactory txmgr, Object... arguments);
}
