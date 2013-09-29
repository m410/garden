package us.m410.j8.action;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public final class PathExpr implements Comparable<PathExpr> {
    private String[] tokens;

    public PathExpr(final String expr) {
        tokens = toArray(expr);
    }

    private PathExpr(final String[] rootExpr, final String[] subExpr) {
        tokens = ArrayUtils.addAll(rootExpr, subExpr);
    }

    public PathExpr append(final PathExpr path) {
        return new PathExpr(tokens, path.tokens);
    }

    public PathExpr append(final String path) {
        return new PathExpr(tokens, toArray(path));
    }

    public String[] getTokens() {
        return tokens;
    }

    public boolean doesPathMatch(final HttpServletRequest request) {
        final String context = request.getContextPath();
        final String[] uri;

        if(context.compareTo("") != 0)
            uri = toArray(request.getRequestURI().replace(context,""));
        else
            uri = toArray(request.getRequestURI());

        if(tokens.length == uri.length) {
            for (int i = 0; i < uri.length; i++) {
                String s = uri[i];

                if(isRegex(tokens[i]) && !isRegexEqual(tokens[i], s))
                    return false;
                else if(!isRegex(tokens[i]) && s.compareTo(tokens[i]) != 0)
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    String[] toArray(final String in) {
        if(in.startsWith("/"))
            return in.substring(1).split("/");
        else
            return in.split("/");

    }
    boolean isRegex(final String i) {
        return i.startsWith("{") && i.endsWith("}");
    }

    boolean isRegexEqual(final String regex, final String value) {
        if(!regex.contains(":")) {
            return true;
        }
        else {
            String expr = regex.substring(regex.indexOf(":")+1, regex.length() - 1);
            Pattern pattern = Pattern.compile(expr);
            Matcher match = pattern.matcher(value);
            return match.find();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,5).append(tokens).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PathExpr)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PathExpr rhs = (PathExpr) obj;
        return new EqualsBuilder()
                .append(this.tokens, rhs.tokens)
                .isEquals();
    }

    @Override
    public int compareTo(PathExpr o) {
        return new CompareToBuilder()
                .append(this.tokens, o.tokens)
                .toComparison();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", ArrayUtils.toString(tokens))
                .toString();
    }
}
