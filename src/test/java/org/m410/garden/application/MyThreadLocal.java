package org.m410.garden.application;

import org.m410.garden.transaction.ThreadLocalSession;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class MyThreadLocal implements ThreadLocalSession {
    private String value;
    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();

    MyThreadLocal(String value) {
        this.value = value;
    }

    public static String get() {
        return myThreadLocal.get();
    }

    public static void set(String a) {
        myThreadLocal.set(a);
    }

    @Override
    public void start() {
        myThreadLocal.set(value);
    }

    @Override
    public void stop() {
        myThreadLocal.remove();
    }
}

