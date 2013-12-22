package org.m410.j8.controller;

import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.http.Action;
import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.controller.action.http.ActionRequest;
import org.m410.j8.controller.action.http.Response;

import java.util.List;

/**
 * This is an interface that all different types of controllers must implement.
 *
 * @author Michael Fortin
 */
public interface Ctlr {

    /**
     * All controllers must implement an action and add it to it's to this list.
  * <p>
     * It's highly recommended that you use the an immutable list implementation
     * form google guava.
     *
     * @return a list of action definitions.
     */
    List<? extends ActionDefinition> actions();

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
