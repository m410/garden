package org.m410.j8.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;

import java.util.List;

/**
 */
public abstract class Controller {
    protected PathExpr pathExpr;
    // default roles
    // default ssl
    // default content-type


    private Controller() {
    }

    protected Controller(PathExpr pathExpr) {
        this.pathExpr = pathExpr;
    }

    public abstract List<ActionDefinition> actions();

    protected final ActionDefinition get(String path, Action act) {
        return act(HttpMethod.GET, act, pathExpr.append(path));
    }

    protected final ActionDefinition post(String path, Action act) {
        return act(HttpMethod.POST, act, pathExpr.append(path));
    }

    protected final ActionDefinition put(String path, Action act) {
        return act(HttpMethod.PUT, act, pathExpr.append(path));
    }

    protected final ActionDefinition delete(String path, Action act) {
        return act(HttpMethod.DELETE, act, pathExpr.append(path));
    }

    private ActionDefinition act(HttpMethod method, Action a, PathExpr p) {
        return new ActionDefinition(a,p,method);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr", pathExpr)
                .toString();
    }
}
