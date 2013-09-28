package us.m410.j8.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class ActionDefinitionImpl implements ActionDefinition {
    private String subPath;
    private Action action;
    private PathExpr pathExpr;

    public ActionDefinitionImpl(String sp, Action a, PathExpr p) {
        pathExpr = p;
        action = a;
        subPath = sp;
    }

    @Override
    public void apply(HttpServletRequest request, HttpServletResponse response) {
        // todo apply action
    }

    @Override
    public boolean doesPathMatch(HttpServletRequest req) {
        return false;  // todo implement me
    }

    @Override
    public ActionStatus status(HttpServletRequest req) {
        return null;
    }
}
