package org.m410.j8.action;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> parametersForRequest(final HttpServletRequest servletRequest) {
        final String[] uri = uriTokens(servletRequest);
        Map<String, String> params = new HashMap();

        for (int i = 0; i < uri.length; i++) {
            if(isUriParam(tokens[i]))
                params.put(uriParamName(tokens[i]), uri[i]);
        }

        return ImmutableMap.copyOf(params);
    }

    private String uriParamName(String token) {
        String innerStr = token.substring(1,token.length() -1);

        if(innerStr.contains(":"))
            return innerStr.substring(0, innerStr.indexOf(":"));
        else
            return innerStr;
    }

    public boolean doesPathMatch(final HttpServletRequest request) {
        final String[] uri = uriTokens(request);

        if(tokens.length == uri.length) {
            for (int i = 0; i < uri.length; i++) {
                String s = uri[i];

                if(isUriParam(tokens[i]) && !isRegexEqual(tokens[i], s))
                    return false;
                else if(!isUriParam(tokens[i]) && s.compareTo(tokens[i]) != 0)
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    protected String[] uriTokens(HttpServletRequest request) {
        final String context = request.getContextPath();

        String[] uri;
        if(context.compareTo("") != 0)
            uri = toArray(request.getRequestURI().replace(context,""));
        else
            uri = toArray(request.getRequestURI());
        return uri;
    }

    String[] toArray(final String in) {
        return Arrays.asList(in.split("/"))
                .stream()
                .filter((s)->{return !s.equals("");})
                .toArray(String[]::new);

//        if(in.startsWith("/"))
//            return in.substring(1).split("/");
//        else
//            return in.split("/");
    }

    boolean isUriParam(final String i) {
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
                .append("path", tokens)
                .toString();
    }

    public String toText() {
        return StringUtils.join(tokens, "/");
    }
}
