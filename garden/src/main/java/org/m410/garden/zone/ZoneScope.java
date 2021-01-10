package org.m410.garden.zone;

/**
 * Controller and Action transaction scope
 *
 * @author Michael Fortin
 */
public enum ZoneScope {

    /**
     * No Transaction
     */
    None,

    /**
     * The transaction is active for the life of the action.
     */
    Action,

    /**
     * The transaction is active for the life of the action and view rendering.
     */
    ActionAndView
}
