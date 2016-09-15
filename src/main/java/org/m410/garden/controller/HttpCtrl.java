package org.m410.garden.controller;

import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.http.HttpActionDefinition;

import java.util.List;

/**
 * This is an interface that all different types of controllers must implement.
 *
 * @author Michael Fortin
 */
public interface HttpCtrl extends Intercept {

    /**
     * All controllers must implement an action and add it to it's to this list.
  * <p>
     * It's highly recommended that you use the an immutable list implementation
     * form google guava.
     *
     * @return a list of action definitions.
     */
    List<? extends HttpActionDefinition> actions();

}
