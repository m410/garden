package org.m410.j8.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;
import org.m410.j8.action.Response;

import java.util.List;

/**
 * This is the default base implementation of a controller.
 */
public abstract class Controller {
    protected PathExpr pathExpr;
    // default roles
    // default ssl
    // default content-type


    /**
     * private constructor so you must implement a path.
     */
    private Controller() {
    }

    /**
     * Creates a controller with the base path expression.
     * @param pathExpr a path expression.
     */
    protected Controller(PathExpr pathExpr) {
        this.pathExpr = pathExpr;
    }

    /**
     * Creates a controller with the base path expression.
     * @param pathExpr a base path.
     */
    protected Controller(String pathExpr) {
        this.pathExpr = new PathExpr(pathExpr);
    }

    /**
     * All controllers must implement an action and add it to it's to this list.
     * It's highly recommended that you use the an immutable list implementation
     * form guava.
     *
     * @return a list of action defintions.
     */
    public abstract List<ActionDefinition> actions();

    /**
     * a basic get action.
     *
     * Typically used like
     * {{
     *  @Override
     *  public List<ActionDefinition> actions() {
     *       return ImmutableList.of(get("", home));
     *  }
     *
     *  Action home = req -> response().withView("/index.jsp");
     * }}
     *
     * @param path the action path expression
     * @param act the action implementation.
     * @return an action definition
     */
    protected final ActionDefinition get(String path, Action act) {
        return act(HttpMethod.GET, act, pathExpr.append(path));
    }

    /**
     * a post action definition
     * @param path the path expression
     * @param act the action
     * @return a new action definition.
     */
    protected final ActionDefinition post(String path, Action act) {
        return act(HttpMethod.POST, act, pathExpr.append(path));
    }

    /**
     * create a put action definition.
     *
     * @param path the path expression
     * @param act the action
     * @return a new action definition.
     */
    protected final ActionDefinition put(String path, Action act) {
        return act(HttpMethod.PUT, act, pathExpr.append(path));
    }

    /**
     * a delete action definition.
     *
     * @param path the path expression
     * @param act the action
     * @return a new action definition.
     */
    protected final ActionDefinition delete(String path, Action act) {
        return act(HttpMethod.DELETE, act, pathExpr.append(path));
    }

    /**
     * A generic action definition builder.
     * @param method the http method.
     * @param a an action.
     * @param p the path expression.
     * @return a new action definition.
     */
    private ActionDefinition act(HttpMethod method, Action a, PathExpr p) {
        return new ActionDefinition(a,p,method);
    }

    /**
     * A helper function used by any action to create a response.
     * @return a new response.
     */
    protected Response response() {
        return new Response();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr", pathExpr != null ? pathExpr.toText() : null)
                .toString();
    }
}
