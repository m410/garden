package org.m410.garden.zone.transactional.fixtures;

import org.m410.garden.zone.Zone;

/**
 * @author m410
 */
public class Trx implements Zone {

    private static int callCount = 0;

    public static int getCallCount() {
        return callCount;
    }

    public static void resetCount() {
        callCount = 0;
    }

    @Override
    public void start() {
        callCount++;
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
