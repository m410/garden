package org.m410.garden.zone.transactional.fixtures;

import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneHandlerFactory;
import org.m410.garden.zone.ZoneManager;
import org.m410.garden.zone.transaction.TrxInvocationHandlerFactory;

/**
 * @author m410
 */
public final class TrxThreadLocalFactory implements ZoneFactory<Trx> {
    private ZoneManager zoneManager;

    @Override
    public void setZoneManager(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public Trx makeZone() {
        return new Trx();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public ZoneHandlerFactory zoneHandlerFactory() {
        return new TrxInvocationHandlerFactory(zoneManager);
    }
}
