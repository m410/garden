package us.m410.j8.action;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import us.m410.j8.action.direction.Direction;
import us.m410.j8.action.status.*;
import us.m410.j8.controller.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class ActionDefinitionImpl implements ActionDefinition, Comparable<ActionDefinition> {
    private Action action;
    PathExpr pathExpr;
    private HttpMethod httpMethod;
    private Direction direction;
    private boolean useSsl;
    private boolean useAuthentication;
    private boolean useAuthorization;
    private String[] allowedContentTypes;

    public ActionDefinitionImpl(Action action, PathExpr pathExpr, HttpMethod httpMethod, Direction direction,
            boolean useSsl, boolean useAuthentication, boolean useAuthorization, String[] allowedContentTypes) {
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.direction = direction;
        this.useSsl = useSsl;
        this.useAuthentication = useAuthentication;
        this.useAuthorization = useAuthorization;
        this.allowedContentTypes = allowedContentTypes;
    }

    public ActionDefinitionImpl(Action action, PathExpr pathExpr, HttpMethod httpMethod, Direction direction) {
        this.action = action;
        this.pathExpr = pathExpr;
        this.httpMethod = httpMethod;
        this.direction = direction;
    }

    @Override
    public void apply(HttpServletRequest request, HttpServletResponse response) {
        action.action(new ActionRequestDefaultImpl(request, pathExpr))
                .handleResponse(request, response);
    }

    @Override
    public boolean doesRequestMatchAction(HttpServletRequest req) {
        return pathExpr.doesPathMatch(req) && httpMethod.toString().equalsIgnoreCase(req.getMethod());
    }

    @Override
    public ActionStatus status(HttpServletRequest req) {
        final boolean path = pathExpr.doesPathMatch(req);

        if(path) {
            if(useSsl && !req.isSecure())
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
        ActionDefinitionImpl that = (ActionDefinitionImpl)o;
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
        if (!(obj instanceof ActionDefinitionImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ActionDefinitionImpl rhs = (ActionDefinitionImpl) obj;
        return new EqualsBuilder()
                .append(this.httpMethod, rhs.httpMethod)
                .append(this.pathExpr, rhs.pathExpr)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("method",httpMethod)
                .append("path", pathExpr)
                .toString();
    }
}
