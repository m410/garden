package org.m410.j8.controller.action.ws;

import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.Securable;
import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.action.http.Action;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * todo not quite defined yet, needs an interface in common with ActionDefinition
 * todo should change ActionDefinition to HttpActionDefinition and put in new package
 *
 * @author m410
 */
public final class WsActionDefinition implements ActionDefinition {
    private static final ActionProtocol DEF_ACTION_PROTOCOL = ActionProtocol.WS;
    private final PathExpr pathExpr;
    private final Ctlr controller;

    private final Securable.State useSsl;
    private final List<String> roles;
    private final boolean isAuthenticated;

    private WebSocket webSocket;

    public WsActionDefinition(PathExpr pathExpr, WebSocket webSocket, Ctlr controller,
                              Securable.State ssl, String[] roles, boolean isAuthenticated) {
        this.pathExpr = pathExpr;
        this.controller = controller;
        this.webSocket = webSocket;
        this.useSsl = ssl;
        this.roles = ImmutableList.<String>builder().addAll(Arrays.asList(roles)).build();
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public PathExpr getPathExpr() {
        return pathExpr;
    }

    @Override
    public Ctlr getController() {
        return controller;
    }

    @Override
    public Action getAction() {
        return null;  // todo only applies to http controllers
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
        return isAuthenticated;
    }

    @Override
    public ActionProtocol getType() {
        return DEF_ACTION_PROTOCOL;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }
}
