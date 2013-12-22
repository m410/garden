package org.m410.j8.controller.action.ws;

import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.Ctlr;

/**
 * todo not quite defined yet, needs an interface in common with ActionDefinition
 * todo should change ActionDefinition to HttpActionDefinition and put in new package
 *
 * @author m410
 */
public class WebSocketDefinition {
    private final PathExpr pathExpr;
    private final Ctlr controller;
    private WsAction wsAction;

    public WebSocketDefinition(PathExpr pathExpr, WsAction wsAction, Ctlr controller) {
        this.pathExpr = pathExpr;
        this.controller = controller;
        this.wsAction = wsAction;
    }

    public PathExpr getPathExpr() {
        return pathExpr;
    }

    public Ctlr getController() {
        return controller;
    }

    public WsAction getWsAction() {
        return wsAction;
    }
}
