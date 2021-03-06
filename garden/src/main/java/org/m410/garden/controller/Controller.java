package org.m410.garden.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.http.HttpMethod;
import org.m410.garden.controller.action.http.Response;
import org.m410.garden.zone.ZoneScope;

/**
 * This is the default implementation of a the Ctlr interface.
 * <p>
 * To create your own application controller add a class like this.
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
 * <p>
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
 * Missing from this release is action discrimination based on content type, securing with ssl,
 * and role based authorization.
 *
 * todo document contentType and ssl in constructor.
 *
 * @author Michael Fortin
 * @see org.m410.garden.controller.action.PathExpr
 * @see org.m410.garden.controller.action.http.Action
 */
public abstract class Controller implements HttpCtlr, Securable {

    /**
     * A helper property to set the content type of action definitions.
     */
    public static final String TEXT_HTML = "text/html";

    /**
     * A helper property to set the content type of action definitions.
     */
    public static final String APPLICATION_JSON = "application/json";

    /**
     * A helper property to set the content type of action definitions.
     */
    public static final String APPLICATION_XML = "application/xml";


    protected PathExpr pathExpr;
    protected boolean useSsl = false;
    protected String[] contentTypes = {};
    protected ZoneScope defaultTransactionScope = ZoneScope.Action;

    /**
     * private constructor so you must implement a path.
     */
    private Controller() {
        this.pathExpr = null;
    }

    /**
     * Creates a controller with the base path expression.
     *
     * @param pathExpr a path expression.
     * @see org.m410.garden.controller.action.PathExpr
     */
    protected Controller(PathExpr pathExpr) {
        this.pathExpr = pathExpr;
    }

    /**
     * Creates a controller with the base path expression.
     *
     * @param pathExpr a base path.
     */
    protected Controller(String pathExpr) {
        this.pathExpr = new PathExpr(pathExpr);
    }

    /**
     * a basic get action.
     * <p>
     * Typically used like:
     * <pre>
     *  public List&gt;ActionDefinition&gt; actions() {
     *       return ImmutableList.of(get("", home));
     *  }
     *  Action home = req -&gt; respond().withView("/index.jsp");
     * </pre>
     *
     * @param path the action path expression
     * @param act  the action implementation.
     * @return an action definition
     */
    protected final HttpActionDefinition get(String path, Action act) {
        return act(HttpMethod.GET, act, pathExpr.append(path));
    }

    /**
     * A post action definition.
     *
     * @param path the path expression
     * @param act  the action
     * @return a new action definition.
     */
    protected final HttpActionDefinition post(String path, Action act) {
        return act(HttpMethod.POST, act, pathExpr.append(path));
    }

    /**
     * create a put action definition.
     *
     * @param path the path expression
     * @param act  the action
     * @return a new action definition.
     */
    protected final HttpActionDefinition put(String path, Action act) {
        return act(HttpMethod.PUT, act, pathExpr.append(path));
    }

    /**
     * a delete action definition.
     *
     * @param path the path expression
     * @param act  the action
     * @return a new action definition.
     */
    protected final HttpActionDefinition delete(String path, Action act) {
        return act(HttpMethod.DELETE, act, pathExpr.append(path));
    }

    /**
     * A generic action definition builder called by all other methods named with
     * by the http method. You may call this directly but the convenience methods
     * are shorter and more readable.  This will also set some default values for the
     * action definition.  It will set the ssl state to Optional, any accept content
     * type, any roles, and transactional scope of none.
     *
     * @param method   the http method.
     * @param action   an action.
     * @param pathExpr the path expression.
     * @return a new action definition.
     */
    protected HttpActionDefinition act(HttpMethod method, Action action, PathExpr pathExpr) {
        return new HttpActionDefinition(this, action, pathExpr, method, Securable.Ssl.Optional,
                new String[]{}, new String[]{}, defaultTransactionScope);
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
