package org.m410.garden.di;


import org.m410.garden.zone.ZoneHandlerFactory;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentFactory<T> {

    // todo should txmgr be optional? Maybe the ZoneManager
    T make(ZoneHandlerFactory txmgr, Object... arguments);
}
