package org.m410.j8.action;


/**
 * This is just a wrapper around IOException so it doesn't have to be caught by users
 * of this api.
 *
 * @author Michael Fortin
 */
public class RuntimeIOException extends RuntimeException {

    protected RuntimeIOException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RuntimeIOException(Throwable cause) {
        super(cause);
    }

    public RuntimeIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeIOException(String message) {
        super(message);
    }

    public RuntimeIOException() {
        super();
    }
}
