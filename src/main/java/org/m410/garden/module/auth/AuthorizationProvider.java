package org.m410.garden.module.auth;

import java.util.Map;
import java.util.Optional;

/**
 * @author m410
 */
public interface AuthorizationProvider {

    public static final String SESSION_KEY = "m410-garden-auth";

    default String loginBaseUri() {
        return "/authorize";
    }

    default String successUri() {
        return "/";
    }

    Optional<User> authorize(Map<String,String> parameters);

}
