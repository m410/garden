package us.m410.j8.orm;

import us.m410.j8.application.SessionStartStop;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ThreadLocalFactory<T extends SessionStartStop> {
    T make();

    /**
     * De-register jdbc drivers.
     */
    void shutdown();
}
