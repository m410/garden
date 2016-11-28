package org.m410.garden.di;


import org.m410.garden.zone.ZoneManager;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ComponentFactory<T> {

    T make(ZoneManager zoneManager, Object... arguments);
}
