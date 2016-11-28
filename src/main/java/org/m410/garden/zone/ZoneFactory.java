package org.m410.garden.zone;


/**
 * A thread local factory used by the application to create thread local
 * instances.
 *
 * @author Michael Fortin
 */
public interface ZoneFactory<T extends Zone> {

    void setZoneManager(ZoneManager zoneManager);

    String name();

    /**
     * Create a threadLocal zone instance.
     * @return a new thread local session instance.
     */
    T makeZone();

    /**
     * Shutdown the factory, and do cleanup like de-registering jdbc drivers.
     */
    void shutdown();

    /**
     *
     * @return the ZoneHandlerFactory associated with this zone.
     */
    ZoneHandlerFactory zoneHandlerFactory();
}
