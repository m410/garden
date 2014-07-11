package org.m410.j8.action;

/**
 * A controller action.  These are created inside controllers and added to
 * ActionDefinitions.
 *
 * @author Michael Fortin
 */
public interface Action {
    public Response action(ActionRequest args);


    // todo need to add an ActionThrows action that looks like this but has throws Exception as part of the method signature
}
