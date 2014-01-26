package org.m410.garden.application;

import org.m410.garden.transaction.ThreadLocalSession;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class My2ThreadLocal implements ThreadLocalSession {
    private String value;

    My2ThreadLocal(String value) {
        this.value = value;
    }

    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<>();
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

