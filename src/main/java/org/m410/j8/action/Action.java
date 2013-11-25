package org.m410.j8.action;

/**
 * A controller action.  These are created inside controllers and added to
 * ActionDefinitions.
 *
 * @author Michael Fortin
 */
public interface Action {
    public ActionResponse action(ActionRequest args);
}
