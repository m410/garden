package org.m410.garden.controller;

import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.http.ActionRequest;
import org.m410.garden.controller.action.http.Response;
import org.slf4j.LoggerFactory;

import static org.m410.garden.controller.action.http.Response.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Add authorization abilities to a controller.  Once a controller implements this
 * interface, the roles can be overridden by the action definition.
 *
 * @see org.m410.garden.controller.action.http.HttpActionDefinition#roles(String...)
 *
 * @author Michael Fortin
 */
public interface Authorizable extends Intercept {

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
    default Response intercept(ActionRequest actionRequest, ActionDefinition action) throws Exception{
        LoggerFactory.getLogger(getClass()).warn("#### Authorizable:{}", actionRequest.identity());

        if(!actionRequest.identity().isAnonymous()) {
            final List<String> userRoles = Arrays.asList(actionRequest.identity().getUserRoles());
            final Optional<String> any = action.getAuthorizedRoles().stream().filter(userRoles::contains).findAny();

            if(any.isPresent()) {
                return action.getAction().execute(actionRequest);
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
