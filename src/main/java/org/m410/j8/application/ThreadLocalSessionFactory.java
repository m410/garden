package org.m410.j8.application;


/**
 * A thread local factory used by the application to create thread local
 * instances.
 *
 * @author Michael Fortin
 */
public interface ThreadLocalSessionFactory<T extends ThreadLocalSession> {

    /**
     * Create a threadLocal instance.
     * @return a new threadlocal instance.
     */
    T make();

    /**
     * Shutdown the factory, and do cleanup like deregistering jdbc drivers.
     */
    void shutdown();
}
