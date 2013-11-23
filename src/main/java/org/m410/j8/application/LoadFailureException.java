package org.m410.j8.application;

/**
 * When the application fails to load.
 *
 * @author Michael Fortin
 */
public class LoadFailureException extends RuntimeException {
    public LoadFailureException(Throwable cause) {
        super(cause);
    }
}
