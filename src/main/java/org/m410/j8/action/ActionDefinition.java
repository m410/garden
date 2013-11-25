package org.m410.j8.action;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.action.status.*;
import org.m410.j8.controller.HttpMethod;
import org.m410.j8.servlet.ServletExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is an action and it's meta data.  It includes it path expression, http method
 * and other information.
 *
 * You don't create one of of these explicitly, it's created on the controller when you
 * use one of the action methods
 *
 * <code>
 * public List<ActionDefinition> actions() {
 *    return ImmutableList.of(get("", home));
 * }
 * </code>
 *
 * @todo missing contentType validation, authentication and authorization
 * @author Michael Fortin
 */
public class ActionDefinition implements  Comparable<ActionDefinition>, ServletExtension {
    private static final Logger log = LoggerFactory.getLogger(ActionDefinition.class);

    private Action action;
    PathExpr pathExpr;
    private HttpMethod httpMethod;
    private boolean useSsl;
    private boolean useAuthentication;
    private boolean useAuthorization;
    private String[] allowedContentTypes;

    public ActionDefinition(Action action, PathExpr pathExpr, HttpMethod httpMethod, boolean useSsl,
            boolean useAuthentication, boolean useAuthorization, String[] allowedContentTypes) {
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.useSsl = useSsl;
        this.useAuthentication = useAuthentication;
        this.useAuthorization = useAuthorization;
        this.allowedContentTypes = allowedContentTypes;
    }

    public ActionDefinition(Action action, PathExpr pathExpr, HttpMethod httpMethod) {
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
    }

    public void apply(HttpServletRequest request, HttpServletResponse response) {
        log.debug("actDef:{}",this);
        action.action(new ActionRequestDefaultImpl(request, pathExpr))
                .handleResponse(request, response);
    }

    public boolean doesRequestMatchAction(HttpServletRequest req) {
        return pathExpr.doesPathMatch(req) && httpMethod.toString().equalsIgnoreCase(req.getMethod());
    }

    public ActionStatus status(HttpServletRequest req) {
        final boolean path = pathExpr.doesPathMatch(req);

        if(path) {
            if(!req.getRequestURI().endsWith(SERVLET_EXT))
                return new Forward(req.getRequestURI());
            else if(useSsl && !req.isSecure())
                return new RedirectToSecure(req.getRequestURI());
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
    public int compareTo(ActionDefinition o) {
        ActionDefinition that = (ActionDefinition)o;
        return new CompareToBuilder()
                .append(this.httpMethod, that.httpMethod)
                .toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,3)
                .append(pathExpr)
                .append(httpMethod)
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
                .append(this.httpMethod, rhs.httpMethod)
                .append(this.pathExpr, rhs.pathExpr)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("method",httpMethod)
                .append("path", pathExpr != null ? pathExpr.toText() : null)
                .toString();
    }
}
