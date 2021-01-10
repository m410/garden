package org.m410.garden.di;

import java.util.function.Supplier;

/**
 * @author Michael Fortin
 */
public class NoServiceException extends RuntimeException {
    public NoServiceException(String message) {
        super(message);
    }
}
