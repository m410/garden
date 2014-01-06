package org.m410.j8.controller;

import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.http.ActionRequest;
import org.m410.j8.controller.action.http.Response;

import static org.m410.j8.controller.action.http.Response.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Add authorization abilities to a controller.  Once a controller implements this
 * interface, the roles can be overridden by the action definition.
 *
 * @see org.m410.j8.controller.action.http.HttpActionDefinition#roles(String...)
 *
 * @author Michael Fortin
 */
public interface Authorizable extends Intercept{

    /**
     * A list of roles that an authenticated user must have in the UserPrincipal object
     * to be able to execute any action in this controller.
     * @return a list of acceptable roles.
     */
    List<String> acceptRoles();

    /**
     * Default implementation of calling an action.  This can be overridden to intercept calls
     * to all actions in the controller.
     *
     * @param actionRequest ActionRequest
     * @param action the definition of the action.
     * @return a Response object, this can be modified to add values to every action in the
     *      controller like a pragma header.
     */
    default Response intercept(ActionRequest actionRequest, ActionDefinition action) {
        if(!actionRequest.userPrincipal().isAnonymous()) {
            final List<String> userRoles = Arrays.asList(actionRequest.userPrincipal().getUserRoles());
            final Optional<String> any = action.getAuthorizedRoles().stream().filter(userRoles::contains).findAny();

            if(any.isPresent()) {
                return action.getAction().action(actionRequest);
            }
            else {
                return respond().withStatus(ERROR_FORBIDDEN);
            }
        }
        else {
            return respond().withStatus(ERROR_FORBIDDEN);
        }
    }
}
