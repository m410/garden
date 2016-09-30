package org.m410.garden.zone.transactional.fixtures;

import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneHandlerFactory;

/**
 * @author m410
 */
public class TrxThreadLocalFactory implements ZoneFactory<Trx> {

    @Override
    public Trx makeZone() {
        return new Trx();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public ZoneHandlerFactory zoneHandlerFactory() {
        return null;
    }
}
