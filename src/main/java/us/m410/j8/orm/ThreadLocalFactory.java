package us.m410.j8.orm;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ThreadLocalFactory {
    ThreadLocal make();

    /**
     * De-register jdbc drivers.
     */
    void shutdown();
}
