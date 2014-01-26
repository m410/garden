package org.m410.garden.transactional.fixtures;

import org.m410.garden.transaction.ThreadLocalSessionFactory;

/**
 * @author m410
 */
public class TrxThreadLocalSessionFactory implements ThreadLocalSessionFactory<TrxSession> {

    @Override
    public TrxSession make() {
        return new TrxSession();
    }

    @Override
    public void shutdown() {

    }
}
