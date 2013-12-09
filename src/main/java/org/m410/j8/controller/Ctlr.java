package org.m410.j8.controller;

import org.m410.j8.action.ActionDefinition;

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
    List<ActionDefinition> actions();
}
