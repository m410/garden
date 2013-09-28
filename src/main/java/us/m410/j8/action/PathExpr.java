package us.m410.j8.action;

/**
 */
public class PathExpr {
    private String expression;

    public PathExpr(String expr) {
        expression = expr;
    }

    public PathExpr(String rootExpr,String subExpr) {
        expression = rootExpr + subExpr;
    }

    public String getExpression() {
        return expression;
    }

    public PathExpr append(PathExpr path) {
        return new PathExpr(expression, path.expression);
    }
}
