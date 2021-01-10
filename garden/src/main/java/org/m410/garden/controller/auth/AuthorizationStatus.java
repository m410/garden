package org.m410.garden.controller.auth;

import java.util.Optional;

/**
 * @author m410
 */
public class AuthorizationStatus<T> {
    private final boolean authorized;
    private final T entity;
    private final String declineMessage;

    AuthorizationStatus(T entity) {
        this.entity = entity;
        authorized = true;
        declineMessage = null;
    }

    AuthorizationStatus(String declineMessage) {
        this.declineMessage = declineMessage;
        this.entity = null;
        this.authorized = false;
    }

    public static <T> AuthorizationStatus<T> accept(T t) {
        return new AuthorizationStatus<>(t);
    }

    public static <T> AuthorizationStatus<T> decline(String message) {
        return new AuthorizationStatus<>(message);
    }

    public static <T> AuthorizationStatus<T> option(Optional<T> t, String message) {
        if(t.isPresent())
            return new AuthorizationStatus<T>(t.get());
        else
            return new AuthorizationStatus<T>(message);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public T get() {
        return entity;
    }

    public String getDeclineMessage() {
        return declineMessage;
    }
}
