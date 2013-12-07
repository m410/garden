package org.m410.j8.controller;

import java.util.List;

/**
 * Add authorization abilities to a controller.
 *
 * @author Michael Fortin
 */
public interface Authorization {

    /**
     * A list of roles that an authenticated user must have in the UserPrincipal object
     * to be able to execute any action in this controller.
     * @return a list of acceptable roles.
     */
    List<String> acceptRoles();
}
