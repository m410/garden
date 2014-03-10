package org.m410.garden.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.http.HttpMethod;
import org.m410.garden.controller.action.http.Response;
import org.m410.garden.controller.action.ws.WebSocket;
import org.m410.garden.controller.action.ws.WsActionDefinition;
import org.m410.garden.transaction.TransactionScope;

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
 * By default, each action is wrapped in a transaction.  If you're using the
 * {@link org.m410.garden.module.jpa.JpaModule}, the entity manager for the request is accessible
 * with the JpaThreadLocal.get() method.
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
public abstract class WsController implements WsCtrl {

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
    protected TransactionScope defaultTransactionScope = TransactionScope.Action;

    /**
     * private constructor so you must implement a path.
     */
    private WsController() {
        this.pathExpr = null;
    }

    /**
     * Creates a controller with the base path expression.
     *
     * @param pathExpr a path expression.
     * @see org.m410.garden.controller.action.PathExpr
     */
    protected WsController(PathExpr pathExpr) {
        this.pathExpr = pathExpr;
    }

    /**
     * Creates a controller with the base path expression.
     *
     * @param pathExpr a base path.
     */
    protected WsController(String pathExpr) {
        this.pathExpr = new PathExpr(pathExpr);
    }

    /**
     * This returns a WebSocket Definition, with takes a WebSocket class with the
     * handler implementations.
     *
     * todo not implemented yet.
     * Note: can't be intercepted.  Don't know how to authorize
     *
     * @param path the path expression
     * @param act  the action
     * @return a new action definition.
     */
    protected final WsActionDefinition ws(String path, WebSocket act) {
        return new WsActionDefinition(pathExpr.append(path), act, this,
                Securable.State.Optional, new String[0], false);
    }

    protected WebSocket socket() {
        return new WebSocket();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr", pathExpr != null ? pathExpr.toText() : null)
                .toString();
    }
}
