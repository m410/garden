package org.m410.j8.application;

import org.m410.j8.transaction.ThreadLocalSessionFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class MyThreadLocalFactory implements ThreadLocalSessionFactory<MyThreadLocal> {
    private String value;

    MyThreadLocalFactory(String value) {
        this.value = value;
    }

    @Override public MyThreadLocal make() { return new MyThreadLocal(value); }
    @Override public void shutdown() { }
}
