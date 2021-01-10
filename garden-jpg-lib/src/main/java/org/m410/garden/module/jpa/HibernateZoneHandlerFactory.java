package org.m410.garden.module.jpa;

import org.m410.garden.zone.ZoneHandlerFactory;
import org.m410.garden.zone.ZoneManager;

import java.lang.reflect.Proxy;

/**
 * @author Michael Fortin
 */
public final class HibernateZoneHandlerFactory implements ZoneHandlerFactory {
    private final ZoneManager zoneManager;

    public HibernateZoneHandlerFactory(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public <T> T proxy(Class<T> interfce, T instance) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Class[] interfaces = {interfce};
        final HibernateHandler<T> handler = new HibernateHandler<T>(instance, zoneManager);

        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }
}
