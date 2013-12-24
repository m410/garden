package org.m410.j8.controller;

/**
 * Added to controllers to mark it as secure.  This applies to all actions in a controller
 * but it can be overridden by an action definition.
 *
 * @see org.m410.j8.controller.action.http.HttpActionDefinition#ssl(org.m410.j8.controller.Securable.State)
 *
 * @author Michael Fortin
 */
public interface Securable {

    /**
     * The Ssl acceptable values.
     */
    static enum State {
        Only,
        Optional,
        Never
    }

    State secureState();
}
