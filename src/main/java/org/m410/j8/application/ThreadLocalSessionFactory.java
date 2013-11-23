package org.m410.j8.application;


/**
 * A thread local factory used by the application to create thread local
 * instances.
 *
 * @author Michael Fortin
 */
public interface ThreadLocalSessionFactory<T extends ThreadLocalSession> {
    T make();

    /**
     * De-register jdbc drivers.
     */
    void shutdown();
}
