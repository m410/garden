package org.m410.j8.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;
import org.m410.j8.action.Response;

import java.util.List;

/**
 * This is the default implementation of a the Ctlr interface.
 * <p>
 * To create your own application controller add a class like this.
  * <p>
 * <pre>
 * public class MyController extends Controller {
 *     public MyController() { super("basepath"); }
 *     public List&lt;ActionDefinition&gt; actions() {
 *         return ImmutableList.of(
 *             get("", home),
 *             get("{id:\\d+}", view)
 *         );
 *     }
 *     Action home = req -&gt; respond().withView("/index.jsp");
 *     Action view = req -&gt; respond().withModel("name","value").withView("/view.jsp");
 * }
 * </pre>
 *
 * The Controller class has a private constructor so you have to write one of your own and
 * call one of the super class constructors that sets the base path expression.
 * <p>
 * There is only one method you must implement, but do do anything useful you have to create
 * actions and return them in the list of actions.
 * <p>
 * In the example above the get methods returns an Action definition, using a sub path expression
 * and an action class.  The Action classes are defined as closures at the bottom of the example.
  * <p>
 * Each Action must return a response instance.
  * <p>
 * By default, each action is wrapped in a transaction.  If you're using the
 * {@link org.m410.j8.module.jpa.JpaModule}, the entity manager for the request is accessible
 * with the JpaThreadLocal.get() method.
  * <p>
 * Missing from this release is action discrimination based on content type, securing with ssl,
 * and role based authorization.
 *
 * @see org.m410.j8.action.PathExpr
 * @see org.m410.j8.action.Action
 *
 * @author Michael Fortin
 */
public abstract class Controller implements Ctlr {
    protected PathExpr pathExpr;
    // todo default ssl
    // todo default content-type

    /**
     * private constructor so you must implement a path.
     */
    private Controller() {
    }

    /**
     * Creates a controller with the base path expression.
     *
     * @see org.m410.j8.action.PathExpr
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
     * a basic get action.
  * <p>
     * Typically used like:
  * <p>
     * <pre>
     *  public List&gt;ActionDefinition&gt; actions() {
     *       return ImmutableList.of(get("", home));
     *  }
     *  Action home = req -&gt; respond().withView("/index.jsp");
     * </pre>
     *
     * @param path the action path expression
     * @param act the action implementation.
     * @return an action definition
     */
    protected final ActionDefinition get(String path, Action act) {
        return act(HttpMethod.GET, act, pathExpr.append(path));
    }

    /**
     * A post action definition.
     *
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
     * A generic action definition builder called by all other methods named with
     * by the http method. You may call this directly but the convenience methods
     * are shorter and more readable.
     *
     * @param method the http method.
     * @param a an action.
     * @param p the path expression.
     * @return a new action definition.
     */
    protected ActionDefinition act(HttpMethod method, Action a, PathExpr p) {
        return new ActionDefinition(a,p,method);
    }

    /**
     * A helper function used by any action to create a response.
     *
     * @return a new response.
     */
    protected Response respond() {
        return new Response();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr", pathExpr != null ? pathExpr.toText() : null)
                .toString();
    }
}
