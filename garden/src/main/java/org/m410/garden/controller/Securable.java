package org.m410.garden.controller;

/**
 * Added to controllers to mark it as secure.  This applies to all actions in a controller
 * but it can be overridden by an action definition.
 *
 * @see org.m410.garden.controller.action.http.HttpActionDefinition#ssl(org.m410.garden.controller.Securable.Ssl)
 *
 * @author Michael Fortin
 */
public interface Securable {

    /**
     * The Ssl acceptable values.
     */
    static enum Ssl {
        Only,
        Optional,
        Never
    }

}
