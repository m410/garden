package org.m410.garden.zone;

import org.m410.garden.zone.Zone;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class MyZone implements Zone {
    private String value;
    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();

    MyZone(String value) {
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

