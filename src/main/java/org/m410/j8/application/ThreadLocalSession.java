package org.m410.j8.application;

/**
 * Wraps each action request with a thread local session instance.
 *
 * @author Michael Fortin
 */
public interface ThreadLocalSession<T> {
    void start();
    void stop();
}
