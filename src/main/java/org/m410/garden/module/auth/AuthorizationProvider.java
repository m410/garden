package org.m410.garden.module.auth;

import java.util.Map;
import java.util.Optional;

/**
 * @author m410
 */
public interface AuthorizationProvider<T> {

    public static final String SESSION_KEY = "m410-garden-auth";

    default String loginBaseUri() {
        return "/authorize";
    }

    default String successUri() {
        return "/";
    }

    AuthorizationStatus<T> authorize(Map<String,String> parameters);

}
