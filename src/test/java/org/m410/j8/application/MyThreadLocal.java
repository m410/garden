package org.m410.j8.application;

import org.m410.j8.transaction.ThreadLocalSession;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class MyThreadLocal implements ThreadLocalSession {
    private String value;

    MyThreadLocal(String value) {
        this.value = value;
    }

    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();
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

