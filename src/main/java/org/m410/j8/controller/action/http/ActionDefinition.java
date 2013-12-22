package org.m410.j8.controller.action.http;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.controller.action.ControllerAction;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.action.status.*;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.Securable;
import org.m410.j8.servlet.ServletExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * This is an action and it's meta data.  It includes it path expression, http method
 * and other information.
  * <p>
 * You don't create one of of these explicitly, it's created on the controller when you
 * use one of the action methods
 *
 * <pre>
 * public List&lt;ActionDefinition$gt; actions() {
 *    return ImmutableList.of(get("", home));
 * }
 * </pre>
 *
 *
 * todo document contentType validation, authentication and authorization
 *
 * @author Michael Fortin
 */
public final class ActionDefinition implements ControllerAction, ServletExtension {
    private static final Logger log = LoggerFactory.getLogger(ActionDefinition.class);
    private static final ActionProtocol DEF_ACTION_PROTOCOL = ActionProtocol.HTTP;

    private final Ctlr controller;
    private final PathExpr pathExpr;
    private final Securable.State useSsl;
    private final String[] roles;

    private final boolean useAuthentication;
    private final boolean useAuthorization;

    private final Action action;
    private final HttpMethod httpMethod;
    private final String[] contentTypes;

    /**
     * A full constructor setting all parameters of a action definition.  Generally there is no need to
     * call this or the other constructor directly, it's created implicitly by the controllers by one
     * of the actionDefinition methods like {@link org.m410.j8.controller.Controller#get(String, Action)}.
     *
     * @param controller A controller that holds the action.
     * @param action The action implementation.
     * @param pathExpr The path of the action.
     * @param httpMethod The http method of the action.
     * @param useSsl if this action must use ssl.  Only used on Controllers with the Secure interface.
     * @param useAuthentication if this action requires authentication.  Only applies if the controller
     *          implements the Authentication interface.
     * @param useAuthorization if the action requires authorization.  This only applies if the controller
     *          implements the Authentication interface.
     * @param contentTypes The content types that this action will accept.  If it's empty, it accepts all
     *          types.
     * @param roles The roles this action will accept.  This only applies if the controller implements
     *          the Authentication interface.
     */
    public ActionDefinition(Ctlr controller, Action action, PathExpr pathExpr, HttpMethod httpMethod,
            Securable.State useSsl,boolean useAuthentication, boolean useAuthorization, String[] contentTypes,
            String[] roles ) {
        this.controller = controller;
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.useSsl = useSsl;
        this.useAuthentication = useAuthentication;
        this.useAuthorization = useAuthorization;
        this.contentTypes = contentTypes;
        this.roles = roles;
    }

    /**
     * Creates a new instance of a action definition with some default values.  Authorize, authenticate,
     * ssl are all false, and contentTypes and roles are empty.
     *
     * @param controller A back reference to the controller holding the action.
     * @param action The action to call.
     * @param pathExpr The path expression for the controller and action.
     * @param httpMethod The Http Method to accept on this definition.
     */
    public ActionDefinition(Ctlr controller, Action action, PathExpr pathExpr, HttpMethod httpMethod) {
        this.controller = controller;
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.useSsl = Securable.State.Optional;
        this.useAuthentication = false;
        this.useAuthorization = false;
        this.contentTypes = new String[0];
        this.roles = new String[0];
    }

    /**
     * Returns a new Action definition with the updated content types.
     *
     * @param contentTypes An array of content types or an empty array for all types.
     * @return a new ActionDefinition
     */
    public ActionDefinition contentTypes(String... contentTypes) {
        return new ActionDefinition(controller, action,pathExpr,httpMethod,useSsl,
                useAuthentication,useAuthorization,contentTypes, roles);
    }

    /**
     * Updates the ActionDefinition with the new list of roles to accept.
     *
     * @param roles The list of roles, or empty for all roles.
     * @return A new ActionDefinition.
     */
    public ActionDefinition roles(String... roles) {
        return new ActionDefinition(controller, action,pathExpr,httpMethod,useSsl,
                useAuthentication,useAuthorization,contentTypes, roles);

    }

    /**
     *
     * @param ssl
     * @return
     */
    public ActionDefinition ssl(Securable.State ssl) {
        return new ActionDefinition(controller, action,pathExpr,httpMethod,ssl,
                useAuthentication,useAuthorization,contentTypes, roles);

    }

    public void apply(HttpServletRequest request, HttpServletResponse response) {
        log.debug("actDef:{}",this);
        final ActionRequestDefaultImpl actionRequest = new ActionRequestDefaultImpl(request, pathExpr);
        controller.intercept(actionRequest, action).handleResponse(request, response);
    }

    public boolean doesRequestMatchAction(HttpServletRequest req) {
        // todo check content type
        return pathExpr.doesPathMatch(req) && httpMethod.toString().equalsIgnoreCase(req.getMethod());
    }

    public ActionStatus status(HttpServletRequest req) {
        final boolean path = pathExpr.doesPathMatch(req);

        // todo check for Secure interface
        // todo check authorization interface

        if(path) {
            if(!req.getRequestURI().endsWith(SERVLET_EXT))
                return new Forward(req.getRequestURI());
//            else if(useSsl && !req.isSecure())
//                return new RedirectToSecure(req.getRequestURI());
            else if(useAuthentication && req.getUserPrincipal() == null)
                return new RedirectToAuth("/auth",req.getRequestURI());
            else if(useAuthorization && (req.getUserPrincipal() == null))
                return Forbidden.getInstance();
            else
                return ActOn.getInstance();
        }
        else
            return NotAnAction.getInstance();
    }

    @Override
    public Ctlr getController() {
        return controller;
    }

    @Override
    public PathExpr getPathExpr() {
        return pathExpr;
    }

    @Override
    public Securable.State getSsl() {
        return useSsl;
    }

    @Override
    public Optional<String[]> getAuthorizedRoles() {
        return Optional.of(roles);
    }

    @Override
    public boolean isAuthenticated() {
        return useAuthentication;
    }

    @Override
    public ActionProtocol getType() {
        return DEF_ACTION_PROTOCOL;
    }

    @Override
    public int compareTo(ControllerAction o) {
        if(o instanceof ActionDefinition) {
            ActionDefinition that = (ActionDefinition)o;
            return new CompareToBuilder()
                    .append(this.getPathExpr(), that.getPathExpr())
                    .append(this.httpMethod, that.httpMethod)
                    .append(this.contentTypes, that.contentTypes)
                    .toComparison();
        }
        else {
            return ControllerAction.super.compareTo(o);
        }
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(DEF_ACTION_PROTOCOL)
                .append(pathExpr)
                .append(httpMethod)
                .append(contentTypes)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ActionDefinition)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ActionDefinition rhs = (ActionDefinition) obj;
        return new EqualsBuilder()
                .append(this.pathExpr, rhs.pathExpr)
                .append(this.httpMethod, rhs.httpMethod)
                .append(this.contentTypes, rhs.contentTypes)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", pathExpr != null ? pathExpr.toText() : null)
                .append("method", httpMethod)
                .append("contentTypes", Arrays.toString(contentTypes))
                .toString();
    }
}
