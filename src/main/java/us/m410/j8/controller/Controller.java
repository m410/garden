package us.m410.j8.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.ActionDefinitionImpl;
import us.m410.j8.action.PathExpr;
import us.m410.j8.action.direction.Direction;
import us.m410.j8.action.direction.Directions;

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

    protected final ActionDefinition get(String path, Action act, Direction direct) {
        return act(HttpMethod.GET, act, pathExpr.append(path), direct);
    }

    protected final ActionDefinition get(String path, Action act) {
        return act(HttpMethod.GET, act, pathExpr.append(path), Directions.noView());
    }

    protected final ActionDefinition post(String path, Action act, Direction direct) {
        return act(HttpMethod.POST, act, pathExpr.append(path), direct);
    }

    protected final ActionDefinition post(String path, Action act) {
        return act(HttpMethod.POST, act, pathExpr.append(path), Directions.noView());
    }

    protected final ActionDefinition put(String path, Action act, Direction direct) {
        return act(HttpMethod.PUT, act, pathExpr.append(path), direct);
    }

    protected final ActionDefinition put(String path, Action act) {
        return act(HttpMethod.PUT, act, pathExpr.append(path), Directions.noView());
    }

    protected final ActionDefinition delete(String path, Action act, Direction direct) {
        return act(HttpMethod.DELETE, act, pathExpr.append(path), direct);
    }

    protected final ActionDefinition delete(String path, Action act) {
        return act(HttpMethod.DELETE, act, pathExpr.append(path), Directions.noView());
    }

    private ActionDefinition act(HttpMethod method, Action a, PathExpr p, Direction direction) {
        return new ActionDefinitionImpl(a,p,method,direction);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pathExpr", pathExpr)
                .toString();
    }
}
