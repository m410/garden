package org.m410.j8.transaction;

/**
 * Controller and Action transaction scope
 *
 * @author Michael Fortin
 */
public enum TransactionScope {
    None,
    Action,
    ActionAndView,
    Service
}
