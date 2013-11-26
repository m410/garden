package org.m410.j8.action;

import java.io.InputStream;
import java.util.Map;

/**
 * This is the argument to the action class.  It encapsulates much of information
 * from a servlet request object.
 *
 * @author Michael Fortin
 */
public interface ActionRequest {

    /**
     * Is there an active session associated with the request.
     *
     * @return yes if there is a session.
     */
    boolean isActiveSession();

    /**
     * Same as the http servlet request principal.
     * @return a UserPrincipal, never null,
     */
    UserPrincipal userPrincipal();

    /**
     * session parameters.  If there is no session, this return an empty map.
     *
     * @return a map of session parameters.
     */
    Map<String, Object> session();

    /**
     * the properties of the request.
     * @return RequestProperties, it's never null.
     */
    RequestProperties requestProperties();

    /**
     * A map of request headers.
     * @return map
     */
    Map<String, String> requestHeaders();

    /**
     * the parameters embedded in the url as path expressions.
     *
     * @return always return a map, that may be empty.
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
     * @return an inputstream if the request is a post.
     */
    InputStream postBodyAsStream();

    /**
     * The post body as a string.
     *
     * @throws NotAPostException when it's not a post
     * @return String value of the post.
     */
    String postBodyAsString();
}
