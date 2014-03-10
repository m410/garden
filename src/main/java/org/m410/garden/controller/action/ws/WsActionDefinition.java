package org.m410.garden.controller.action.ws;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.controller.Securable;
import org.m410.garden.controller.WsCtrl;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Action;

import java.util.Arrays;
import java.util.List;

/**
 * todo not quite defined yet, needs an interface in common with ActionDefinition
 * todo should change ActionDefinition to HttpActionDefinition and put in new package
 *
 * @author m410
 */
public final class WsActionDefinition implements ActionDefinition {
    private static final ActionProtocol DEF_ACTION_PROTOCOL = ActionProtocol.WS;
    private final PathExpr pathExpr;
    private final WsCtrl  controller;

    private final Securable.State useSsl;
    private final List<String> roles;
    private final boolean isAuthenticated;

    private WebSocket webSocket;

    public WsActionDefinition(PathExpr pathExpr, WebSocket webSocket, WsCtrl controller,
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
    public HttpCtrl getController() {
        return null;
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
