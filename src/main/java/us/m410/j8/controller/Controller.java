package us.m410.j8.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.ActionDefinitionImpl;
import us.m410.j8.action.PathExpr;
import us.m410.j8.action.direction.Direction;

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

    protected final ActionDefinition get(String path, Action act, Direction defaultDirection) {
        return new ActionDefinitionImpl(path, act, pathExpr);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr",pathExpr)
                .toString();
    }
}
