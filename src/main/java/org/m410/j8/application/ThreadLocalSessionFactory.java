package org.m410.j8.application;

import org.m410.j8.application.ThreadLocalSession;

/**
 * Document Me..
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
