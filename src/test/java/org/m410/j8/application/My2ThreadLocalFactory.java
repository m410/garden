package org.m410.j8.application;

import org.m410.j8.persistence.ThreadLocalFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class My2ThreadLocalFactory implements ThreadLocalFactory<My2ThreadLocal> {
    private String value;

    My2ThreadLocalFactory(String value) {
        this.value = value;
    }

    @Override public My2ThreadLocal make() { return new My2ThreadLocal(value); }
    @Override public void shutdown() { }
}
