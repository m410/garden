package us.m410.j8.action;

import javax.servlet.http.HttpServletRequest;

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
}
