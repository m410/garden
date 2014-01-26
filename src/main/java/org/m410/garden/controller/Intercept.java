package org.m410.garden.controller;

import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.ActionRequest;
import org.m410.garden.controller.action.http.Response;

/**
 * Wraps every controller action call with an interceptor.
 *
 * @author Michael Fortin
 */
public interface Intercept {

    /**
     * Default implementation of calling an action.  This can be overridden to intercept calls
     * to all actions in the controller.
     *
     * @param actionRequest ActionRequest
     * @param action the definition of the action.
     * @return a Response object, this can be modified to add values to every action in the
     *      controller like a pragma header.
     */
    default Response intercept(ActionRequest actionRequest, Action action) {
        return action.action(actionRequest);
    }
}
