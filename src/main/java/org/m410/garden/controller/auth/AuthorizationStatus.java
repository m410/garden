package org.m410.garden.controller.auth;

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

    public AuthorizationStatus<T> accept(T t) {
        return new AuthorizationStatus<>(t);
    }

    public AuthorizationStatus<T> decline(String message) {
        return new AuthorizationStatus<>(message);
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
