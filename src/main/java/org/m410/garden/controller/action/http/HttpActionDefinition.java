package org.m410.garden.controller.action.http;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.garden.controller.Authorizable;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.status.*;
import org.m410.garden.controller.Ctlr;
import org.m410.garden.controller.Securable;
import org.m410.garden.module.auth.AuthorizationProvider;
import org.m410.garden.servlet.ServletExtension;
import org.m410.garden.transaction.TransactionScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
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
public final class HttpActionDefinition implements ActionDefinition, ServletExtension {
    private static final Logger log = LoggerFactory.getLogger(HttpActionDefinition.class);
    private static final ActionProtocol DEF_ACTION_PROTOCOL = ActionProtocol.HTTP;

    private final Ctlr controller;
    private final PathExpr pathExpr;
    private final Securable.State useSsl;
    private final List<String> roles;

    private final boolean useAuthentication;

    private final Action action;
    private final HttpMethod httpMethod;
    private final List<String> acceptTypes;

    private final TransactionScope transactionScope;

    /**
     * A full constructor setting all parameters of a action definition.  Generally there is no need to
     * call this or the other constructor directly, it's created implicitly by the controllers by one
     * of the actionDefinition methods like {@link org.m410.garden.controller.Controller#get(String, Action)}.
     *
     * @param controller A controller that holds the action.
     * @param action The action implementation.
     * @param pathExpr The path of the action.
     * @param httpMethod The http method of the action.
     * @param useSsl if this action must use ssl.  Only used on Controllers with the Secure interface.
     * @param acceptTypes The content types that this action will accept.  If it's empty, it accepts all
     *          types.
     * @param roles The roles this action will accept.  This only applies if the controller implements
     *          the Authentication interface.
     * @param transactionScope the transactional scope for the action request.
     */
    public HttpActionDefinition(Ctlr controller, Action action, PathExpr pathExpr, HttpMethod httpMethod,
                                Securable.State useSsl, String[] acceptTypes, String[] roles,
                                TransactionScope transactionScope) {
        this.controller = controller;
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.useSsl = useSsl;
        this.useAuthentication = controller instanceof Authorizable;
        this.acceptTypes = ImmutableList.<String>builder().addAll(Arrays.asList(acceptTypes)).build();
        this.roles = ImmutableList.<String>builder().addAll(Arrays.asList(roles)).build();
        this.transactionScope = transactionScope;
    }

    /**
     * Returns a new Action definition with the updated content types.
     *
     * @param contentTypes An array of content types or an empty array for all types.
     * @return a new ActionDefinition
     */
    public HttpActionDefinition accept(String... contentTypes) {
        return new HttpActionDefinition(controller, action,pathExpr,httpMethod,
                useSsl,contentTypes, roles.toArray(new String[roles.size()]),transactionScope);
    }

    /**
     * Updates the ActionDefinition with the new list of roles to accept.
     *
     * @param roles The list of roles, or empty for all roles.
     * @return A new ActionDefinition.
     */
    public HttpActionDefinition roles(String... roles) {
        return new HttpActionDefinition(controller, action,pathExpr,httpMethod,
                useSsl , acceptTypes.toArray(new String[acceptTypes.size()]), roles,transactionScope);
    }

    /**
     *
     * @param ssl the acceptable ssl states
     * @return a new HttpActionDefinition
     */
    public HttpActionDefinition ssl(Securable.State ssl) {
        return new HttpActionDefinition(controller, action,pathExpr,httpMethod,ssl,
                acceptTypes.toArray(new String[acceptTypes.size()]), roles.toArray(new String[roles.size()]),
                transactionScope);
    }

    /**
     *
     * @param transactionScope the scope of the transaction
     * @return a new HttpActionDefinition
     */
    public HttpActionDefinition transaction(TransactionScope transactionScope) {
        return new HttpActionDefinition(controller, action,pathExpr,httpMethod,useSsl,
                acceptTypes.toArray(new String[acceptTypes.size()]), roles.toArray(new String[roles.size()]),
                transactionScope);

    }

    public void apply(HttpServletRequest request, HttpServletResponse response) {
        log.debug("action definition:{}",this);
        final ActionRequestDefaultImpl actionRequest = new ActionRequestDefaultImpl(request, pathExpr);
        controller.intercept(actionRequest, action).handleResponse(request, response);
    }

    public boolean doesRequestMatchAction(HttpServletRequest req) {
        return pathExpr.doesPathMatch(req) &&
                httpMethod.toString().equalsIgnoreCase(req.getMethod()) &&
                (acceptTypes.size() == 0 || acceptTypes.contains(req.getContentType()));
    }

    public ActionStatus status(HttpServletRequest req) {
        final boolean path = pathExpr.doesPathMatch(req);

        if(path) {
            if(!req.getRequestURI().endsWith(SERVLET_EXT))
                return new Forward(req.getRequestURI());

            else if(useSsl == Securable.State.Only && !req.isSecure())
                return new RedirectToSecure(req.getRequestURI());

            else if(useAuthentication &&
                    (req.getSession(false) == null ||
                    req.getSession(false).getAttribute(AuthorizationProvider.SESSION_KEY) == null))
                // should use req.getUserPrincipal() == null
                // todo this needs to be configurable somehow
                // todo needs a reference to the authorizationProvider?
                return new RedirectToAuth("/authorize",req.getRequestURI());

            //todo need to check user roles
//            else if(useAuthorization && (req.getUserPrincipal() == null))
//                return Forbidden.getInstance();


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
    public Action getAction() {
        return action;
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
    public List<String> getAuthorizedRoles() {
        return roles;
    }

    @Override
    public boolean isAuthenticated() {
        return useAuthentication;
    }

    @Override
    public ActionProtocol getType() {
        return DEF_ACTION_PROTOCOL;
    }

    public TransactionScope getTransactionScope() {
        return transactionScope;
    }

    @Override
    public int compareTo(ActionDefinition o) {
        if(o instanceof HttpActionDefinition) {
            HttpActionDefinition that = (HttpActionDefinition)o;
            return new CompareToBuilder()
                    .append(this.getPathExpr(), that.getPathExpr())
                    .append(this.httpMethod, that.httpMethod)
                    .append(this.acceptTypes, that.acceptTypes)
                    .toComparison();
        }
        else {
            return ActionDefinition.super.compareTo(o);
        }
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(DEF_ACTION_PROTOCOL)
                .append(pathExpr)
                .append(httpMethod)
                .append(acceptTypes)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HttpActionDefinition)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        HttpActionDefinition rhs = (HttpActionDefinition) obj;
        return new EqualsBuilder()
                .append(this.pathExpr, rhs.pathExpr)
                .append(this.httpMethod, rhs.httpMethod)
                .append(this.acceptTypes, rhs.acceptTypes)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", pathExpr != null ? pathExpr.toText() : null)
                .append("method", httpMethod)
                .append("contentTypes", acceptTypes)
                .toString();
    }
}
