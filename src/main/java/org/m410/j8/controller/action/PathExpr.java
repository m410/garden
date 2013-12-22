package org.m410.j8.controller.action;

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

import org.m410.j8.servlet.ServletExtension;


/**
 * A partial path expression for a controller or a full path expression for an
 * action.
 * <p>
 *
 * Path expressions are a restful uri, with variables embedded in it that
 * can extracted into parameters to be used by the application.
 * <p>
 *
 * Path variables are wrapped in curly braces and can have embedded regular expressions.
 * <p>
 *
 * For example the path "/clients/{id}" is a path with a dynamic id parameter.
 * <p>
 *
 * A path with a regular expression matcher could look like "/clients/{id:\\d+}".  This
 * would only match urls where the id is a number.  You should use a regex where ever
 * possible, for example these two urls will work with the regex and without, they both will
 * match the url, and the first one found will be used.

 * <ul>
 *     <li>/clients/{id}/users/{id:\\d+}</li>
 *     <li>/clients/{name}/users/{id:\\d+}</li>
 * </ul>

 * Url parameters are available to an in action using the {@link org.m410.j8.controller.action.http.ActionRequest#urlParameters()}
 * argument.
 *
 * @author Michael Fortin
 */
public final class PathExpr implements Comparable<PathExpr>, ServletExtension {
    private final String[] tokens;

    /**
     * Creates a path expression from a string.
     *
     * @param expr a string uri with optional variables.
     */
    public PathExpr(final String expr) {
        tokens = toArray(expr);
    }

    /**
     */
    PathExpr(final String[] rootExpr, final String[] subExpr) {
        tokens = ArrayUtils.addAll(rootExpr, subExpr);
    }

    /**
     * Controllers will have a base path, and this append the actions path to it.
     *
     * @param path the action path
     * @return a new path expression.
     */
    public PathExpr append(final PathExpr path) {
        return new PathExpr(tokens, path.tokens);
    }

    /**
     * creates a new Path expression appending on the given string expression.
     *
     * @param path a partial uri path expression.
     * @return a new PathExpr.
     */
    public PathExpr append(final String path) {
        return new PathExpr(tokens, toArray(path));
    }

    public String[] getTokens() {
        return tokens;
    }

    /**
     * Uses this path expression to extract the parameters from a uri.
     *
     * @param servletRequest the servlet request.
     * @return a map of variables deducted from the url.
     */
    public Map<String, String> parametersForRequest(final HttpServletRequest servletRequest) {
        final String[] uri = uriTokens(servletRequest);
        Map<String, String> params = new HashMap<>();

        for (int i = 0; i < uri.length; i++) {
            if (isUriParam(tokens[i]))
                params.put(uriParamName(tokens[i]), uri[i]);
        }

        return ImmutableMap.copyOf(params);
    }

    private String uriParamName(String token) {
        String innerStr = token.substring(1, token.length() - 1);

        if (innerStr.contains(":"))
            return innerStr.substring(0, innerStr.indexOf(":"));
        else
            return innerStr;
    }

    /**
     * Checks to see if this path matches the request uri.
     *
     * @param request the servlet request.
     * @return true if it's a match
     */
    public boolean doesPathMatch(final HttpServletRequest request) {
        final String[] uri = uriTokens(request);

        if (tokens.length == uri.length) {
            for (int i = 0; i < uri.length; i++) {
                String s = uri[i];

                if (isUriParam(tokens[i]) && !isRegexEqual(tokens[i], s))
                    return false;
                else if (!isUriParam(tokens[i]) && s.compareTo(tokens[i]) != 0)
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    protected String[] uriTokens(HttpServletRequest request) {
        final String context = request.getContextPath();
        final String requestURI = withoutExtension(request.getRequestURI());

        String[] uri;

        if (context.compareTo("") != 0)
            uri = toArray(requestURI.replace(context, ""));
        else
            uri = toArray(requestURI);

        return uri;
    }

    protected String withoutExtension(String uri) {
        if (uri.endsWith(SERVLET_EXT))
            return uri.substring(0, uri.length() - 5);
        else
            return uri;
    }

    protected String[] toArray(final String in) {
        return Arrays.asList(in.split("/"))
                .stream()
                .filter((s) -> {
                    return !s.equals("");
                })
                .toArray(String[]::new);
    }

    protected boolean isUriParam(final String i) {
        return i.startsWith("{") && i.endsWith("}");
    }

    protected boolean isRegexEqual(final String regex, final String value) {
        if (!regex.contains(":")) {
            return true;
        } else {
            String expr = regex.substring(regex.indexOf(":") + 1, regex.length() - 1);
            Pattern pattern = Pattern.compile(expr);
            Matcher match = pattern.matcher(value);
            return match.find();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 5).append(tokens).hashCode();
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

    /**
     * a shorter toString representation of this path.
     *
     * @return a string
     */
    public String toText() {
        return StringUtils.join(tokens, "/");
    }
}
