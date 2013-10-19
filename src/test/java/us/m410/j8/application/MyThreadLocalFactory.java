package us.m410.j8.application;

import us.m410.j8.persistence.ThreadLocalFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
class MyThreadLocalFactory implements ThreadLocalFactory<MyThreadLocal> {
    private String value;

    MyThreadLocalFactory(String value) {
        this.value = value;
    }

    @Override public MyThreadLocal make() { return new MyThreadLocal(value); }
    @Override public void shutdown() { }
}
