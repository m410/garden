package org.m410.garden.controller.auth;

import java.util.Map;

/**
 * @author m410
 */
public interface AuthenticationProvider<T> {

    public static final String SESSION_KEY = "m410-garden-auth";

    default String loginBaseUri() {
        return "/authorize";
    }

    default String successUri() {
        return "/";
    }

    AuthorizationStatus<T> authorize(Map<String,String> parameters);

}
