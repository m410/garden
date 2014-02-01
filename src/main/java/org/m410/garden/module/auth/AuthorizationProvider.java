package org.m410.garden.module.auth;

import java.util.Map;
import java.util.Optional;

/**
 * @author m410
 */
public interface AuthorizationProvider {

    default String loginUri() {
        return "/authorize";
    }

    default String logoutUri() {
        return "/authorize";
    }

    Optional<User> authorize(Map<String,String> parameters);

}
