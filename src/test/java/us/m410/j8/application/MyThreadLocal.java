package us.m410.j8.application;

import us.m410.j8.orm.ThreadLocalFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
final class MyThreadLocal implements SessionStartStop {
    private String value;

    MyThreadLocal(String value) {
        this.value = value;
    }

    private static final ThreadLocal<String> myThreadLocal = new ThreadLocal();
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

