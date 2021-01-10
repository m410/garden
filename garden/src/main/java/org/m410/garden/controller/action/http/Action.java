package org.m410.garden.controller.action.http;

/**
 * A controller action.  These are created inside controllers and added to
 * ActionDefinitions.
 *
 * @author Michael Fortin
 */
public interface Action {
    public Response execute(ActionRequest args) throws Exception;
}
