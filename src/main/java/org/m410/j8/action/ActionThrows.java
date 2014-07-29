package org.m410.j8.action;

/**
 * A controller action.  These are created inside controllers and added to
 * ActionDefinitions.  This one throws an exception so you don't have to
 * catch ones in your application.
 *
 * @author Michael Fortin
 */
public interface ActionThrows {
    public Response action(ActionRequest args) throws Exception;
}
