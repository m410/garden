package org.m410.j8.action;

import javax.servlet.http.HttpServletRequest;

/**
 * Makes the request properties accessible from the ActionRequest object.
 *
 * todo finish me...
 * @author Michael Fortin
 */
public class RequestProperties {
    private HttpServletRequest servletRequest;

    RequestProperties(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }


}
