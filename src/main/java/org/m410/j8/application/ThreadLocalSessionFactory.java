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
     * @return
     */
    T make();

    /**
     * Shutdown the factory, and do cleanup like deregistering jdbc drivers.
     */
    void shutdown();
}
