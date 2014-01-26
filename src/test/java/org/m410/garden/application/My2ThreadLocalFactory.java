package org.m410.garden.application;

import org.m410.garden.transaction.ThreadLocalSessionFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class My2ThreadLocalFactory implements ThreadLocalSessionFactory<My2ThreadLocal> {
    private String value;

    My2ThreadLocalFactory(String value) {
        this.value = value;
    }

    @Override public My2ThreadLocal make() { return new My2ThreadLocal(value); }
    @Override public void shutdown() { }
}
