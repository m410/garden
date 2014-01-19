package org.m410.j8.transactional.fixtures;

import org.m410.j8.transaction.ThreadLocalSession;

/**
 * @author m410
 */
public class TrxSession implements ThreadLocalSession {

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
