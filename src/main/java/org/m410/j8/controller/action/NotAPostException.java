package org.m410.j8.controller.action;


/**
 * This is just a wrapper around IOException so it doesn't have to be caught by users
 * of this api.
 *
 * @author Michael Fortin
 */
public class NotAPostException extends RuntimeException {

    protected NotAPostException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotAPostException(Throwable cause) {
        super(cause);
    }

    public NotAPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAPostException(String message) {
        super(message);
    }

    public NotAPostException() {
        super();
    }
}
