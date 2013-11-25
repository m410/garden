package org.m410.j8.action;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Much like the servlet request principal object, except it's not null when
 * there is no principal.  Check the isAnonymous method to check if
 * if it's present or not.
 *
 * @author Michael Fortin
 */
public class UserPrincipal {
    private HttpServletRequest httpServletRequest;

    public UserPrincipal(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * username if available.
     * @return name
     */
    public String getUserName() {
        final Principal principal = httpServletRequest.getUserPrincipal();
        if(principal != null)
            return principal.getName();
        else
            return null;
    }

    /**
     * The users authorized roles.
     * @return a string array of roles, or an empty array.
     */
    public String[] getUserRoles() {
        return new String[]{};
    }

    /**
     * true if the session is anonymous.
     *
     * @return true if anonymous.
     */
    public boolean isAnonymous() {
        return httpServletRequest.getUserPrincipal() != null;
    }
}
