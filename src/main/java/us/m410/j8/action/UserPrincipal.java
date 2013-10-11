package us.m410.j8.action;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class UserPrincipal {
    private HttpServletRequest httpServletRequest;

    public UserPrincipal(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getUserName() {
        final Principal principal = httpServletRequest.getUserPrincipal();
        if(principal != null)
            return principal.getName();
        else
            return null;
    }

    public String[] getUserRoles() {
        return new String[]{};
    }

    public boolean isAnonymous() {
        return httpServletRequest.getUserPrincipal() != null;
    }
}
