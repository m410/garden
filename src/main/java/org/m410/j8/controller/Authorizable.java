package org.m410.j8.controller;

import java.util.List;

/**
 * Add authorization abilities to a controller.  Once a controller implements this
 * interface, the roles can be overridden by the action definition.
 *
 * @see org.m410.j8.action.ActionDefinition#roles(String...)
 *
 * @author Michael Fortin
 */
public interface Authorizable {

    /**
     * A list of roles that an authenticated user must have in the UserPrincipal object
     * to be able to execute any action in this controller.
     * @return a list of acceptable roles.
     */
    List<String> acceptRoles();
}
