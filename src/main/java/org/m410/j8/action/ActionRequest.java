package org.m410.j8.action;

import java.io.InputStream;
import java.util.Map;

/**
 * This is the argument to the action class.  It encapsulates much of information
 * from a servlet request object.  It's an interface so it can ve easily mocked
 * for unit testing.
 * <p>
 * Note that there is no need to get the application scope, it can be injected to
 * a controller from the application.
 *
 * @author Michael Fortin
 */
public interface ActionRequest {

    /**
     * Is there an active session associated with the request.  This should
     * be called as a replacement for the more traditional way of determining
     * if there is a session, like
     * <code>if(request.getSession(false) != null)</code>
     *
     * @return yes if there is a session.
     */
    boolean isActiveSession();

    /**
     * Same as the http servlet request principal, but also with user role
     * information when available.
     *
     * @return a UserPrincipal, never null. To see if there is an authenticated
     * user check {@link UserPrincipal#isAnonymous()}
     */
    UserPrincipal userPrincipal();

    /**
     * session parameters.  If there is no session, this return an empty map.
     *
     * @return a map of session parameters.  It's never null, just empty if there
     * is no session.  To check if there is a session use {@link #isActiveSession()}
     */
    Map<String, Object> session();

    /**
     * The properties of the HttpServletRequest.
     *
     * @return RequestProperties, it's never null.
     */
    RequestProperties requestProperties();

    /**
     * A map of request headers.
     *
     * @return map never null.
     */
    Map<String, String> requestHeaders();

    /**
     * the parameters embedded in the url as path expressions.
     *
     * @return always returns a map, that may be empty.
     */
    Map<String, String> urlParameters();

    /**
     * The http servlet request parameters.
     *
     * @return map of string and string array values.
     */
    Map<String, String[]> requestParameters();

    /**
     * The body of the request as an input stream.
     *
     * @throws NotAPostException when it's not a post
     * @return an InputStream if the request is a post.
     */
    InputStream postBodyAsStream();

    /**
     * The post body as a string.  This is suitable for requests when you know the content of
     * the post is modest and it is a string that can be utf8 encoded.
     *
     * @throws NotAPostException when it's not a post
     * @return String value of the post.
     */
    String postBodyAsString();
}
