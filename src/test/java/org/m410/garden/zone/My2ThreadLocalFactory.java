package org.m410.garden.zone;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class My2ThreadLocalFactory implements ZoneFactory<My2Zone> {
    private String value;

    My2ThreadLocalFactory(String value) {
        this.value = value;
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
