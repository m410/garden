package org.m410.j8.transactional.fixtures;

import org.m410.j8.transaction.ThreadLocalSessionFactory;

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
