package org.m410.garden.zone;


/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class MyThreadLocalFactory implements ZoneFactory<MyZone> {
    private String value;

    MyThreadLocalFactory(String value) {
        this.value = value;
    }

    @Override public MyZone makeZone() { return new MyZone(value); }
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
