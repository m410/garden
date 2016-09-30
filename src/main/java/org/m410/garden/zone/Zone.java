package org.m410.garden.zone;

/**
 * Wraps each action request with a thread local session instance.
 *
 * @author Michael Fortin
 */
public interface Zone<T> {
    void start();
    void stop();
}
