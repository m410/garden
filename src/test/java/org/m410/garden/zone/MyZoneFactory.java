package org.m410.garden.zone;


/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MyZoneFactory implements ZoneFactory<MyZone> {
    private String value;
    private ZoneManager zoneManager;

    @Override
    public void setZoneManager(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    public MyZoneFactory(String value) {
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
        return new ZoneHandlerFactory() {
            @Override
            public <T> T proxy(Class<T> interfce, T instance) {
                return instance;
            }
        };
    }
}
