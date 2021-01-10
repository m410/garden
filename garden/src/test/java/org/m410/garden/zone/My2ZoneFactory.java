package org.m410.garden.zone;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class My2ZoneFactory implements ZoneFactory<My2Zone> {
    private String value;

    My2ZoneFactory(String value) {
        this.value = value;
    }

    @Override
    public void setZoneManager(ZoneManager zoneManager) {
    }

    @Override public My2Zone makeZone() { return new My2Zone(value); }
    @Override public void shutdown() { }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public ZoneHandlerFactory zoneHandlerFactory() {
        return null;
    }
}
