package org.m410.j8.controller.action.ws;

import org.m410.j8.controller.Securable;
import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;

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
    private final String[] roles;
    private final boolean isAuthenticated;

    private WebSocket webSocket;

    public WsActionDefinition(PathExpr pathExpr, WebSocket webSocket, Ctlr controller,
                              Securable.State ssl, String[] roles, boolean isAuthenticated) {
        this.pathExpr = pathExpr;
        this.controller = controller;
        this.webSocket = webSocket;
        this.useSsl = ssl;
        this.roles = roles;
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
    public Securable.State getSsl() {
        return useSsl;
    }

    @Override
    public Optional<String[]> getAuthorizedRoles() {
        return Optional.of(roles);
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
