package org.m410.garden.zone;

import org.m410.garden.application.Application;

import java.util.List;

/**
 * @author Michael Fortin
 */
public final class ZoneManager {

    private final List<ZoneFactory> zoneFactories;

    public ZoneManager(List<ZoneFactory> zoneFactories) {
        this.zoneFactories = zoneFactories;
        this.zoneFactories.forEach(z -> z.setZoneManager(this));
    }

    public ZoneFactory byName(String name) {
        return zoneFactories.stream().filter(zoneFactory -> zoneFactory.name().equals(name)).findFirst().orElse(null);
    }

    public List<ZoneFactory> getZoneFactories() {
        return zoneFactories;
    }

    /**
     * Wraps action invocations with a thread local context.
     *
     * @param work internal closure to wrap the action.
     * @return when it's called by the filter or an action this can and should return null, when
     *  it's called to wrap a service call, it should be the the result of the method invocation.
     * @throws Exception everything by default.
     */
    public final Object doInZone(Application.Work work) throws Exception {
        return doWithThreadLocal(zoneFactories, work);
    }

    /**
     * This is part of the plumbing of the application that you shouldn't need to change.  It's
     * called by the application to wrap each request within a thread local context.
     *
     * @param tlf   list of ThreadLocalFactory objects
     * @param block an internal worker closure.
     * @return in most cases it will be null, except when wrapping the call to a service method.
     * @throws Exception everything by default.
     */
    Object doWithThreadLocal(List<? extends ZoneFactory> tlf, Application.Work block) throws Exception {
        if (tlf != null && tlf.size() >= 1) {
            Zone zone = tlf.get(tlf.size() - 1).makeZone();
            zone.start();

            try {
                return doWithThreadLocal(tlf.subList(0, tlf.size() - 1), block);
            }
            finally {
                zone.stop();
            }
        }
        else {
            return block.get();
        }
    }
}
